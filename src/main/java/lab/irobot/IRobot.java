package lab.irobot;

public class IRobot
{
    Cleaner cleaner = new CleanerImpl();
    Speaker workingSpeaker = new SpeakerImpl();

    public void cleanRoom()
    {
        workingSpeaker.msg("started...");
        cleaner.clean();
        workingSpeaker.msg("finished...");
    }
}
