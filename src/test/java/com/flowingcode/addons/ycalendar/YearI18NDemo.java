/*-
 * #%L
 * Year Month Calendar Add-on
 * %%
 * Copyright (C) 2021 - 2025 Flowing Code
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

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@CssImport(value = "./styles/test_year-month-calendar.css", themeFor = "vaadin-month-calendar")
@PageTitle("i18n")
@DemoSource
@Route(value = "year-month-calendar/year-i18n", layout = YearMonthCalendarDemoView.class)
public class YearI18NDemo extends Div {

  public YearI18NDemo() {

    Locale locale = Locale.GERMAN;

    DatePickerI18n i18n = new DatePickerI18n();
    DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
    i18n.setMonthNames(List.of(dfs.getMonths()));
    i18n.setWeekdays(List.of(dfs.getWeekdays()).stream().skip(1).toList());
    i18n.setWeekdaysShort(List.of(dfs.getShortWeekdays()).stream().skip(1).toList());

    DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
    i18n.setFirstDayOfWeek(firstDayOfWeek.getValue() % 7);

    YearCalendar calendar = new YearCalendar();
    // #if vaadin eq 0
    calendar.setClassNameGenerator(date -> {
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
        return "weekend";
      }
      return null;
    });
    // #endif
    calendar.setI18n(i18n);
    add(calendar);
  }

}
