package lab.irobot;

public class CleanerImpl implements Cleaner
{
    @InjectRandomInt(min=3, max=7)
    private int repeat;

    @Override
    public void clean()
    {
        for(int idx = 0; idx < repeat; idx++)
        {
            System.out.println("working....");
        }
    }
}
