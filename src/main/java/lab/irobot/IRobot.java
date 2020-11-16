package lab.irobot;

public class IRobot
{
    Cleaner cleaner = ObjectFactory.getInstance().createObject(Cleaner.class);
    Speaker workingSpeaker = ObjectFactory.getInstance().createObject(Speaker.class);

    public void cleanRoom()
    {
        workingSpeaker.msg("started...");
        cleaner.clean();
        workingSpeaker.msg("finished...");
    }
}
