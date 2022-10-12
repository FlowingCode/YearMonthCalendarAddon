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

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;
import java.time.chrono.HijrahChronology;
import java.util.Arrays;

@DemoSource
@PageTitle("Month")
@CssImport(value = "./styles/test_year-month-calendar.css", themeFor = "vaadin-month-calendar")
@Route(value = "year-month-calendar/month-hijrah", layout = YearMonthCalendarDemoView.class)
public class MonthDemoHijrah extends Div {

  public MonthDemoHijrah() {

    MonthCalendar calendar = new MonthCalendar(HijrahChronology.INSTANCE.dateNow());

    calendar.setClassNameGenerator(date -> {
      if (TestUtils.isHijrahHoliday(date)) {
        return "holiday";
      }
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
        return "weekend";
      }
      return null;
    });

    DatePicker.DatePickerI18n i18n = new DatePicker.DatePickerI18n();
    i18n.setMonthNames(Arrays.asList("al-Muḥarram", "Ṣafar", "Rabīʿ al-ʾAwwal", "Rabīʿ ath-Thānī",
        "Rabīʿ al-ʾĀkhir", "Jumādā al-ʾŪlā", "Jumādā ath-Thāniyah", "Jumādā al-ʾĀkhirah", "Rajab",
        "Shaʿbān", "Ramaḍān", "Shawwāl", "Ḏū al-Qaʿdah", "Ḏū al-Ḥijjah"));
    // calendar.setI18n(i18n);

    Span selectedDate = new Span();
    calendar.addDateSelectedListener(ev -> {
      selectedDate.setText("Selected date: " + ev.getDate());
    });

    Span instructions = new Span("Use arrow keys to move.");
    add(new HorizontalLayout(instructions, selectedDate), calendar);
  }

}
