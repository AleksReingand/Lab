package lab.irobot;


import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ObjectFactory
{
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config = new JavaConfig();
    private List<ObjectConfigurator> configurators = new ArrayList<>();

    public static ObjectFactory getInstance()
    {
        return ourInstance;
    }

    @SneakyThrows
    private ObjectFactory()
    {
        Reflections scanner = new Reflections("lab");
        Set<Class<? extends ObjectConfigurator>> classes = scanner.getSubTypesOf(ObjectConfigurator.class);
        for(Class<? extends ObjectConfigurator> aClass : classes)
        {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }

    @SneakyThrows
    public <T> T createObject(Class<T> clazz)
    {
        clazz = resolveImpl(clazz);
        T t = create(clazz);
        configure(t);
        initStart(t);

        return t;
    }

    @SneakyThrows
    private <T> void initStart(T t)
    {
        Class<?> clazz = t.getClass();
        Set<Method> initMethods = ReflectionUtils.getAllMethods(clazz, method -> method.isAnnotationPresent(Init.class));
        for(Method initMethod : initMethods)
        {
            initMethod.invoke(t);
        }
    }

    private <T> void configure(T t)
    {
        configurators.forEach(configurator -> configurator.configure(t));
    }

    private <T> T create(Class<T> clazz) throws InstantiationException, IllegalAccessException, java.lang.reflect.InvocationTargetException, NoSuchMethodException
    {
        T t = clazz.getDeclaredConstructor().newInstance();
        return t;
    }

    private <T> Class<T> resolveImpl(Class<T> clazz)
    {
        if(clazz.isInterface())
        {
            clazz = config.getImplClass(clazz);
        }
        return clazz;
    }
}
