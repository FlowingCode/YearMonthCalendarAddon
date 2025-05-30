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
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.DayOfWeek;

@CssImport(value = "./styles/test_year-month-calendar.css", themeFor = "vaadin-month-calendar")
@PageTitle("Read only")
@DemoSource
@Route(value = "year-month-calendar/year-readonly", layout = YearMonthCalendarDemoView.class)
public class YearReadonlyDemo extends Div {

  public YearReadonlyDemo() {

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

    calendar.setReadOnly(true);
    calendar.setYear(2022);
    add(calendar);
  }

}
