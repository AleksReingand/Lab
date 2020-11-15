package lab.irobot;


public class SpeakerImpl implements Speaker
{
    @Override
    public void msg(String msg)
    {
        System.out.println(msg);
    }
}
