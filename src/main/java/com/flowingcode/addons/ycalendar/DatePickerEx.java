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

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.function.ValueProvider;
import java.time.LocalDate;

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

}
