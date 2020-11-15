package lab.alarm_radio_watch;

import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

@NoArgsConstructor
public class RadioAlarm implements Alarm, Radio
{
    @Delegate(excludes = AlarmExclusios.class)
    private Alarm alarm = new AlarmImpl();

    @Delegate
    private Radio radio = new RadioImpl();

    @Override
    public void c()
    {
        System.out.println("new time");
    }
}
