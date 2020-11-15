package lab.irobot;

import lombok.SneakyThrows;

public class CleanerImpl implements Cleaner
{
    @Override
    @SneakyThrows
    public void clean()
    {
        System.out.println("working....");
        Thread.sleep(2000);
    }
}
