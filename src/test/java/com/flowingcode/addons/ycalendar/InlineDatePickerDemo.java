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
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;
import java.util.Objects;

@DemoSource
@PageTitle("Inline DatePicker")
@Route(value = "year-month-calendar/inline-date-picker", layout = YearMonthCalendarDemoView.class)
public class InlineDatePickerDemo extends Div {

  public InlineDatePickerDemo() {

    InlineDatePicker field = new InlineDatePicker();
    // #if vaadin eq 0
    add(new LocaleSelector(field::setI18n));
    // #endif

    field.addValueChangeListener(ev->{
      Notification.show("Date changed to: " + Objects.toString(ev.getValue()));
    });

    field.setClassNameGenerator(date -> {
      if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
        return "weekend";
      } else if (TestUtils.isPublicHoliday(date)) {
        return "holiday";
      } else {
        return null;
      }
    });

    add(field);
  }

}

