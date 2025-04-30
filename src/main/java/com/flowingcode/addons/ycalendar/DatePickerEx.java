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

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.function.ValueProvider;
import elemental.json.Json;
import elemental.json.JsonObject;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

@SuppressWarnings("serial")
@Tag("fc-date-picker")
@JsModule("./fc-date-picker/fc-date-picker.js")
public class DatePickerEx extends DatePicker {

  private ValueProvider<LocalDate, String> classNameGenerator;

  /**
   * Default constructor.
   */
  public DatePickerEx() {
    super();
  }

  /**
   * Convenience constructor to create a date picker with a pre-selected date in current UI locale
   * format.
   *
   * @param initialDate the pre-selected date in the picker
   */
  public DatePickerEx(LocalDate initialDate) {
    this();
    setValue(initialDate);
  }

  /**
   * Sets the function that generates CSS class names for days in this calendar.
   *
   * Returning {@code null} from the generator results in no custom class name being set. Multiple
   * class names can be returned from the generator as space-separated.
   *
   * @param classNameGenerator the {@code ValueProvider} to use for generating class names
   */
  public void setClassNameGenerator(ValueProvider<LocalDate, String> classNameGenerator) {
    this.classNameGenerator = classNameGenerator;
    refreshAll();
  }

  /** Refresh the styles of all dates in the displayed year and month. */
  public void refreshAll() {
    // getElement().executeJs("setTimeout(()=>this._clearEmptyDaysStyle())");
  }

  @ClientCallable
  private JsonObject fetchStyles(String yearMonthStr) {
    YearMonth yearMonth = YearMonth.parse(yearMonthStr);
    JsonObject monthStyles = Json.createObject();
    for (int i = 1, n = yearMonth.lengthOfMonth(); i <= n; i++) {
      LocalDate date = yearMonth.atDay(i);
      Optional.ofNullable(classNameGenerator).map(g -> g.apply(date)).ifPresent(className -> {
        monthStyles.put(Integer.toString(date.getDayOfMonth()), className);
      });
    }
    return monthStyles;
  }

}
