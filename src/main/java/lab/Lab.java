package lab;

import lab.alarm_radio_watch.RadioAlarm;
import lab.irobot.IRobot;
import lab.irobot.ObjectFactory;
import lombok.SneakyThrows;


public class Lab
{
    @SneakyThrows
    public static void main(String[] args)
    {
        //System.out.println(EmployeeUtils.getSumSalary(Arrays.asList(new Employee(1, "Aleks", 20), new Employee(2, "Tea",40))));
        //System.out.println(WordsUtils.culculatorWords(new File("testLines.txt")));

//        DistributionService service = new DistributionService();
//        while (true)
//        {
//            service.sendMail();
//            Thread.sleep(500);
//        }

//        Panel panel = new Panel(new Graphics(1, "black"));
//        panel.drawShape(new Circle());
//        panel.setGraph(new Graphics(2, "white"));
//        panel.drawShape(new Point());

        RadioAlarm radio = new RadioAlarm();
        radio.c();

        ObjectFactory.getInstance().createObject(IRobot.class).cleanRoom();
    }
}
