/*-
 * #%L
 * Template Add-on
 * %%
 * Copyright (C) 2021 Flowing Code
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

import com.vaadin.flow.component.html.Div;
import java.time.DayOfWeek;
import java.time.YearMonth;

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

    add(calendar);
  }

}
