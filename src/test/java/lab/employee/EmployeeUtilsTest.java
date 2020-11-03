package lab.employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EmployeeUtilsTest {

    List<Employee> list = new ArrayList<>();
    List<String> sortedList = new ArrayList<>();

    @Before
    public void data()
    {
        Employee aleks = new Employee(1, "Aleks", 10);
        Employee sergey = new Employee(2, "Sergey", 20);
        Employee tea = new Employee(3, "Tea", 30);
        list.add(aleks);
        list.add(sergey);
        list.add(tea);

        sortedList.add(tea.name);
        sortedList.add(aleks.name);
        sortedList.add(sergey.name);
    }


    @Test
    public void sortedNameList()
    {
        List<String> result = EmployeeUtils.nameList(list);
        Assert.assertEquals(result, sortedList);
    }
}