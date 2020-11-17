package lab.irobot;

public class IRobot
{
    @InjectByType
    Cleaner cleaner;
    @InjectByType
    Speaker speaker;

    public void cleanRoom()
    {
        speaker.msg("started...");
        cleaner.clean();
        speaker.msg("finished...");
    }
}
