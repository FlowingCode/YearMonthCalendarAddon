/*-
 * #%L
 * Year Month Calendar Add-on
 * %%
 * Copyright (C) 2021 - 2023 Flowing Code
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.lang.reflect.Method;
import java.time.DayOfWeek;

@DemoSource
@PageTitle("Year")
@CssImport(value = "./styles/test_year-month-calendar.css", themeFor = "vaadin-month-calendar")
@Route(value = "year-month-calendar/year", layout = YearMonthCalendarDemoView.class)
public class YearDemo extends Div {

  private static final Method setHasControlsMethod =
      TestUtils.getMethod(IntegerField.class, "setHasControls", boolean.class).orElse(null);
  private static final Method setStepButtonsVisibleMethod =
      TestUtils.getMethod(IntegerField.class, "setStepButtonsVisible", boolean.class).orElse(null);

  public YearDemo() {
    addClassName("year-demo");
    YearCalendar calendar = new YearCalendar();
    calendar.setClassNameGenerator(date -> {
      if (TestUtils.isPublicHoliday(date)) {
        return "holiday";
      }
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
        return "weekend";
      }
      return null;
    });

    calendar.addYearChangedListener(e -> Notification.show("Year " + e.getYear() + " selected"));

    Span selectedDate = new Span();
    calendar.addDateSelectedListener(ev -> {
      selectedDate.setText("Selected date: " + ev.getDate());
    });

    Span instructions = new Span("Use arrow keys or Ctrl+arrow keys to move.");

    IntegerField yearField = new IntegerField();
    try {
      if (setHasControlsMethod != null) {
        // Vaadin 14/22/23
        setHasControlsMethod.invoke(yearField, true);
      } else if (setStepButtonsVisibleMethod != null) {
        // Vaadin 24
        setStepButtonsVisibleMethod.invoke(yearField, true);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    yearField.setValue(calendar.getYear());
    yearField.addValueChangeListener(e -> calendar.setYear(e.getValue()));

    add(new HorizontalLayout(instructions, selectedDate), yearField, calendar);
  }

}

