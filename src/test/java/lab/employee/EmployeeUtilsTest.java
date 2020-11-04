package lab.employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EmployeeUtilsTest {

    List<Employee> list = new ArrayList<>();
    List<String> sortedList = new ArrayList<>();
    Map<Seniorities, List<Employee>> map = new HashMap<>();

    @Before
    public void data()
    {
        Employee aleks = new Employee(1, "Aleks", 10000);
        Employee sergey = new Employee(2, "Sergey", 20000);
        Employee tea = new Employee(3, "Tea", 30000);
        Employee gary = new Employee(4, "Gary", 30000);
        list.add(aleks);
        list.add(sergey);
        list.add(tea);
        list.add(gary);

        sortedList.add(tea.name);
        sortedList.add(gary.name);
        sortedList.add(aleks.name);
        sortedList.add(sergey.name);

        map.put(Seniorities.JUNIOR, new ArrayList<>(Arrays.asList(aleks)));
        map.put(Seniorities.MIDDLE, new ArrayList<>(Arrays.asList(sergey)));
        map.put(Seniorities.SENIORS, new ArrayList<>(Arrays.asList(tea, gary)));
    }


    @Test
    public void sortedNameList()
    {
        List<String> result = EmployeeUtils.nameList(list);
        Assert.assertEquals(sortedList, result);
    }

    @Test
    public void sortByType()
    {
        Map<Seniorities, List<Employee>> result = EmployeeUtils.sortByType(list);
        Assert.assertEquals(map, result);
    }
}