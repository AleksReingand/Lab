package lab;

import lab.date.DateUtils;
import lab.employee.Employee;
import lab.employee.EmployeeUtils;
import lab.text.WordsUtils;

import java.io.File;
import java.util.Arrays;

public class Lab
{
    public static void main(String[] args)
    {
        System.out.println(EmployeeUtils.getSumSalary(Arrays.asList(new Employee(1, "Aleks", 20), new Employee(2, "Tea",40))));
        System.out.println(WordsUtils.culculatorWords(new File("testLines.txt")));

        DateUtils.friday13(1980, 1990);
    }
}
