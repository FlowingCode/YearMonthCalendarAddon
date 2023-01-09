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

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.function.SerializableFunction;
import java.time.LocalDate;
import java.util.Optional;

@SuppressWarnings("serial")
@Tag("fc-inline-date-picker")
@JsModule("./fc-inline-date-picker/fc-inline-date-picker.js")
public class InlineDatePicker extends AbstractSinglePropertyField<InlineDatePicker, LocalDate> implements HasSize, HasTheme {

  private static final String VALUE_PROPERTY = "value";
  
  private static <R,S> SerializableFunction<R,S> map(SerializableFunction<R,S> f) {
    return r->Optional.ofNullable(r).map(f).orElse(null);
  }
  
  public InlineDatePicker() {
    super(VALUE_PROPERTY, null, String.class, map(LocalDate::parse), map(LocalDate::toString));
    setValue(LocalDate.now());
    setWeekNumbersVisible(true);
  }

  /**
   * Set the week number visible in the DatePicker.
   *
   * <p>Set true to display ISO-8601 week numbers in the calendar.
   *
   * <p>Notice that displaying week numbers is only supported when i18n.firstDayOfWeek is 1
   * (Monday).
   *
   * @param weekNumbersVisible the boolean value to set
   */
  public void setWeekNumbersVisible(boolean weekNumbersVisible) {
    getElement().setProperty("showWeekNumbers", weekNumbersVisible);
  }

  /**
   * Get the state of {@code showWeekNumbers} property of the datepicker
   *
   * <p>This property is not synchronized automatically from the client side, so the returned value
   * may not be the same as in client side.
   *
   * @return the {@code showWeekNumbers} property from the datepicker
   */
  public boolean isWeekNumbersVisible() {
    return getElement().getProperty("showWeekNumbers", false);
  }

}
