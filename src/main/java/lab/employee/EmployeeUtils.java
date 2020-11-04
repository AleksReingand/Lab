package lab.employee;

import java.time.temporal.ValueRange;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
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

    public static Map<Seniorities, List<Employee>> sortByType(List<Employee> employees)
    {
        return employees.stream().collect(Collectors.groupingBy(EmployeeUtils::sortByRange));
    }

    private static Seniorities sortByRange(Employee e)
    {
        return Arrays.stream(Seniorities.values())
                .filter(s -> ValueRange.of(s.getSalaryMin(), s.getSalaryMax()).isValidValue(e.getSalary()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong employee salary"));
    }
}
