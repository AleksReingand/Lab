package lab.employee;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeUtils
{
    public static long getSumSalary(List<Employee> employees)
    {
        return employees.stream().mapToLong(Employee::getSalary).sum();
    }

    public static List<String> nameList(List<Employee> employees)
    {
        return employees.stream().map(Employee::getName).sorted(Comparator.comparingInt(String::length)).peek(System.out::println).collect(Collectors.toList());
    }
}
