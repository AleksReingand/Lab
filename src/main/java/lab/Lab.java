package lab;

import lab.alarm_radio_watch.RadioAlarm;
import lab.irobot.IRobot;
import lab.irobot.ObjectFactory;
import lombok.SneakyThrows;


public class Lab
{
    static int a = 1111;
    static
    {
        a = a-- - --a;
    }
    static
    {
        a = a++ + ++a;
    }


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

//        RadioAlarm radio = new RadioAlarm();
//        radio.c();
//
//        ObjectFactory.getInstance().createObject(IRobot.class).cleanRoom();

//        int a = 1;
//        int b = 0;
//        int c = a/b;
//        System.out.println(c);

        Integer i1 = 128;
        Integer i2 = 128;
        System.out.println(i1 == i2);

        Integer i3 = 127;
        Integer i4 = 127;
        System.out.println(i3 == i4);

//        System.out.println(a);

        double s = 3/0;
        System.out.println(s);

    }
}
