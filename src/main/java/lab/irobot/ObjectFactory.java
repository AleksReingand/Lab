package lab.irobot;


import lombok.SneakyThrows;

public class ObjectFactory
{
    private static ObjectFactory ourInstance = new ObjectFactory();
    private Config config = new JavaConfig();
    public static ObjectFactory getInstance()
    {
        return ourInstance;
    }

    @SneakyThrows
    public <T> T createObject(Class<T> clazz)
    {
        if(clazz.isInterface())
        {
            clazz = config.getImplClass(clazz);
        }

        T c = clazz.getDeclaredConstructor().newInstance();
        
        return c;
    }
}
