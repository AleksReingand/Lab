package lab;

import lab.employee.Employee;
import lab.employee.EmployeeUtils;
import lab.text.WordsUtils;

import java.io.File;
import java.util.Arrays;

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
    }
}
