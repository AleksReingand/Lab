package lab;

import lab.employee.Employee;

import java.util.List;

public class EmployeeUtils
{
    public static long getSumSalary(List<Employee> employees)
    {
        return employees.stream().mapToLong(Employee::getSalary).sum();
    }
}
