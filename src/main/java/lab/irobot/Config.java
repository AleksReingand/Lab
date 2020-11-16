package lab.irobot;

public interface Config
{
    <T> Class<T> getImplClass(Class<T> clazz);
}
