package lab.irobot;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class InjectRandomIntAnnotationObjectConfigurator implements ObjectConfigurator
{
    @SneakyThrows
    @Override
    public void configure(Object t)
    {
        Class<?> clazz = t.getClass();
        Set<Field> allField = ReflectionUtils.getAllFields(clazz, field -> field.isAnnotationPresent(InjectRandomInt.class));

        for(Field field : allField)
        {
            InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            int min = annotation.min();
            int max = annotation.max();
            int value = ThreadLocalRandom.current().nextInt(min, max);
            field.setAccessible(true);
            field.set(t, value);
        }
    }
}
