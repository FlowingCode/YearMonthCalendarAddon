package com.flowingcode.addons.ycalendar;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestUtils {

  private static final Set<LocalDate> HOLIDAYS = new HashSet<>();
  static {
    HOLIDAYS.addAll(computeEasterHolidays(2021, 4, 4));
    HOLIDAYS.addAll(computeEasterHolidays(2022, 4, 17));
    HOLIDAYS.addAll(computeEasterHolidays(2023, 4, 9));

    HOLIDAYS.add(LocalDate.of(2021, 5, 24));
    HOLIDAYS.add(LocalDate.of(2021, 6, 21));
    HOLIDAYS.add(LocalDate.of(2022, 6, 17));
    HOLIDAYS.add(LocalDate.of(2021, 8, 16));
    HOLIDAYS.add(LocalDate.of(2022, 8, 15));
    HOLIDAYS.add(LocalDate.of(2021, 10, 8));
    HOLIDAYS.add(LocalDate.of(2021, 10, 11));
    HOLIDAYS.add(LocalDate.of(2022, 10, 7));
    HOLIDAYS.add(LocalDate.of(2022, 10, 10));
    HOLIDAYS.add(LocalDate.of(2021, 11, 20));
    HOLIDAYS.add(LocalDate.of(2021, 11, 22));
    HOLIDAYS.add(LocalDate.of(2022, 11, 20));
    HOLIDAYS.add(LocalDate.of(2022, 11, 21));
    HOLIDAYS.add(LocalDate.of(2022, 12, 9));
    HOLIDAYS.add(LocalDate.of(2022, 12, 25));
  }

  private static List<LocalDate> computeEasterHolidays(int y, int m, int d) {
    LocalDate easterSunday = LocalDate.of(y, m, d);
    return Arrays.asList(
        easterSunday.plusDays(-2),
        easterSunday.plusDays(-3),
        easterSunday.plusDays(-47),
        easterSunday.plusDays(-48));
  }

  public static boolean isPublicHoliday(LocalDate date) {
    if (HOLIDAYS.contains(date)) {
      return true;
    }
    switch (date.getMonth()) {
      case JANUARY:
        return date.getDayOfMonth() == 1;
      case MARCH:
        return date.getDayOfMonth() == 24;
      case APRIL:
        return date.getDayOfMonth() == 2;
      case MAY:
        return date.getDayOfMonth() == 1
            || date.getDayOfMonth() == 25;
      case JUNE:
        return date.getDayOfMonth() == 20;
      case JULY:
        return date.getDayOfMonth() == 9;
      case DECEMBER:
        return date.getDayOfMonth() == 8
            || date.getDayOfMonth() == 25;
      default:
        return false;
    }
  }

}
