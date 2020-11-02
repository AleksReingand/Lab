package lab;

import lab.employee.Employee;

import java.util.Arrays;

public class Lab
{
    public static void main(String[] args)
    {
        System.out.println(EmployeeUtils.getSumSalary(Arrays.asList(new Employee(1, 20), new Employee(2, 40))));
    }
}
