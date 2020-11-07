package lab.date;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils
{
  public static void friday13(int yearStart, int yearEnd)
  {
    Stream.iterate(yearStart, y -> y + 1)
            .limit(yearEnd - yearStart)
            .collect(Collectors.toMap(year -> year, DateUtils::getAmountFri13))
            .entrySet()
            .stream()
            .sorted((v1, v2) -> v2.getValue() - v1.getValue())
            .forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
  }

  private static int getAmountFri13(Integer year)
  {
    return (int)Arrays.stream(Month.values())
            .filter(month -> LocalDate.of(year, month, 13).getDayOfWeek().equals(DayOfWeek.FRIDAY))
            .count();
  }
}
