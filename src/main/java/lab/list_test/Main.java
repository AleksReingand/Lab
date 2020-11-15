package lab.list_test;

import java.util.ArrayList;
import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        List<String> list = new ArrayList<String>();
        for(int idx = 0; idx < 100; idx++)
        {
            list.add(idx + "");
        }
        System.out.println(list.size());
        list.clear();
        list.add("new list");
        System.out.println(list.size());
    }
}
