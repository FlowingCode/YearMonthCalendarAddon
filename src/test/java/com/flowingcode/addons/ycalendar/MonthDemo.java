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
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;
import java.time.YearMonth;

@DemoSource
@PageTitle("Month")
@CssImport(value = "./styles/test_year-month-calendar.css", themeFor = "vaadin-month-calendar")
@Route(value = "year-month-calendar/month", layout = YearMonthCalendarDemoView.class)
public class MonthDemo extends Div {

  public MonthDemo() {

    MonthCalendar calendar = new MonthCalendar(YearMonth.now());

    calendar.setClassNameGenerator(date -> {
      if (TestUtils.isPublicHoliday(date)) {
        return "holiday";
      }
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
        return "weekend";
      }
      return null;
    });

    Span selectedDate = new Span();
    calendar.addDateSelectedListener(ev -> {
      selectedDate.setText("Selected date: " + ev.getDate());
    });

    Span instructions = new Span("Use arrow keys to move.");
    add(new HorizontalLayout(instructions, selectedDate), calendar);
  }

}
