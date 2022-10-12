/*-
 * #%L
 * Year Month Calendar Add-on
 * %%
 * Copyright (C) 2021 - 2022 Flowing Code
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.flowingcode.addons.ycalendar;

import java.time.LocalDate;
import java.time.chrono.HijrahDate;
import java.time.temporal.ChronoField;
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

  public static boolean isHijrahHoliday(LocalDate date) {
    HijrahDate hdate = HijrahDate.from(date);
    switch (hdate.get(ChronoField.MONTH_OF_YEAR)) {
      case 1:
        // 1st Muharram
        return hdate.get(ChronoField.DAY_OF_MONTH) == 1;
      case 10:
        // 1st Shawwal
        return hdate.get(ChronoField.DAY_OF_MONTH) == 1;
      case 12:
        // 10 Dhul Hijjah
        return hdate.get(ChronoField.DAY_OF_MONTH) == 10;
      default:
        return false;
    }
  }

}
