package lab.date;

import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils
{
  public static void friday13(int yearStart, int yearEnd)
  {
    Stream.iterate(yearStart, y -> y + 1)
            .limit(yearEnd)
            .collect(Collectors.groupingBy(DateUtils::getAmountFri13, Collectors.counting())).entrySet()
            .stream()
            .sorted()
            .forEach((entry) -> System.out.println(entry.getKey() + " " + entry.getValue()));
  }

  private static int getAmountFri13(Integer y)
  {
    return 1;
  }
}
