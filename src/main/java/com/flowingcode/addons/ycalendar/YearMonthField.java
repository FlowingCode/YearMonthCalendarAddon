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
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.internal.JsonSerializer;
import elemental.json.Json;
import elemental.json.JsonValue;
import java.time.YearMonth;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("serial")
@Tag("fc-year-month-field")
@JsModule("./fc-year-month-field/fc-year-month-field.js")
public class YearMonthField extends AbstractSinglePropertyField<YearMonthField, YearMonth>
    implements HasTheme {

  private static final String VALUE_PROPERTY = "value";

  private YearMonth max;
  private YearMonth min;

  private static <R,S> SerializableFunction<R,S> map(SerializableFunction<R,S> f) {
    return r->Optional.ofNullable(r).map(f).orElse(null);
  }

  /** Constructs a new YearMonthField initializing its value to the current year and month. */
  public <P> YearMonthField() {
    super(VALUE_PROPERTY, null, String.class, map(YearMonth::parse), map(YearMonth::toString));
    setValue(YearMonth.now());
  }  
  
  /**
   * Sets the i18n object.
   *
   * @param i18n the DatepickerI18n object used to initialize i18n
   */
  public void setI18n(DatePickerI18n i18n) {
    Objects.requireNonNull(i18n, "The I18N properties object should not be null");
    getElement().setPropertyJson("i18n", JsonSerializer.toJson(i18n));
  }
  
  /**
   * Sets the minimum year/month in the field.
   *
   * @param min the minimum year/month that is allowed to be selected, or <code>null</code> to
   *        remove any minimum constraints
   */
  public void setMin(YearMonth min) {
    JsonValue value = min == null ? Json.createNull()
        : Json.parse("{'month': " + min.getMonth().ordinal() + ", 'year': " + min.getYear() + "}");
    getElement().setPropertyJson("min", value);
    this.min = min;
  }

  /**
   * Gets the minimum year/month in the field.
   *
   * @return the minimum year/month that is allowed to be selected, or <code>null</code> if there's
   *         no minimum
   */
  public YearMonth getMin() {
    return min;
  }

  /**
   * Sets the maximum year/month in the field.
   *
   * @param max the maximum year/month that is allowed to be selected, or <code>null</code> to
   *        remove any maximum constraints
   */
  public void setMax(YearMonth max) {
    JsonValue value = max == null ? Json.createNull()
        : Json.parse("{'month': " + max.getMonth().ordinal() + ", 'year': " + max.getYear() + "}");
    getElement().setPropertyJson("max", value);
    this.max = max;
  }

  /**
   * Gets the maximum year/month in the field.
   *
   * @return the maximum year/month that is allowed to be selected, or <code>null</code> if there's
   *         no maximum
   */
  public YearMonth getMax() {
    return max;
  }

}
