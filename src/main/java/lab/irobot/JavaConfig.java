package lab.irobot;

import java.util.HashMap;
import java.util.Map;

public class JavaConfig implements Config
{
    private Map<Class, Class> ifc2ImplClass = new HashMap<>();

    public JavaConfig()
    {
        ifc2ImplClass.put(Speaker.class, SpeakerImpl.class);
        ifc2ImplClass.put(Cleaner.class, CleanerImpl.class);
    }


    @Override
    public <T> Class<T> getImplClass(Class<T> clazz)
    {
        return ifc2ImplClass.get(clazz);
    }
}
