package lab.irobot;

public class IRobot
{
    @InjectByType
    Cleaner cleaner;
    @InjectByType
    Speaker speaker;

    @Init
    public void init()
    {
        System.out.println(cleaner.getClass().getName());
    }

    public void cleanRoom()
    {
        speaker.msg("started...");
        cleaner.clean();
        speaker.msg("finished...");
    }
}
