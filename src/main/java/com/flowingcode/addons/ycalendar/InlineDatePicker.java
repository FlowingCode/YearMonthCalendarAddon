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

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.internal.JsonSerializer;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

@SuppressWarnings("serial")
@Tag("fc-inline-date-picker")
@JsModule("./fc-inline-date-picker/fc-inline-date-picker.js")
@Uses(YearMonthField.class)
@Uses(MonthCalendar.class)
public class InlineDatePicker extends AbstractSinglePropertyField<InlineDatePicker, LocalDate> implements HasSize, HasTheme {

  private static final String VALUE_PROPERTY = "value";

  private ValueProvider<LocalDate, String> classNameGenerator;

  private YearMonth yearMonth;

  private static <R,S> SerializableFunction<R,S> map(SerializableFunction<R,S> f) {
    return r->Optional.ofNullable(r).map(f).orElse(null);
  }
  
  /** Creates a new instance of InlineDatePicker initialized with the current date and with week numbers visible. */
  public InlineDatePicker() {
    super(VALUE_PROPERTY, null, String.class, map(LocalDate::parse), map(LocalDate::toString));
    setValue(LocalDate.now());
    setWeekNumbersVisible(true);
    getElement().addEventListener("month-change", event -> {
      setYearMonth(YearMonth.parse(event.getEventData().getString("event.detail.value")));
    }).addEventData("event.detail.value");
  }

  /**
   * Sets whether week numbers should be visible on the calendar.
   * <p>Notice that displaying week numbers is only supported when i18n.firstDayOfWeek is 1
   * (Monday).
   *
   * @param weekNumbersVisible {@code true} if ISO-8601 week numbers should be displayed in the calendar, {@code false} otherwise
   */
  public void setWeekNumbersVisible(boolean weekNumbersVisible) {
    getElement().setProperty("showWeekNumbers", weekNumbersVisible);
  }

  /**
   * Returns the state of the {@code showWeekNumbers} property of the datepicker.
   *
   * <p>Note that this property is not synchronized automatically from the client side, so the returned value
   * may not be the same as in client-side code.</p>
   *
   * @return the state of the {@code showWeekNumbers} property of the datepicker
   */
  public boolean isWeekNumbersVisible() {
    return getElement().getProperty("showWeekNumbers", false);
  }

  /**
   * Sets the i18n object.
   *
   * @param i18n the {@code DatepickerI18n} object used to initialize i18n
   */
  public void setI18n(DatePickerI18n i18n) {
    Objects.requireNonNull(i18n, "The I18N properties object should not be null");
    getElement().setPropertyJson("i18n", JsonSerializer.toJson(i18n));
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
    IntStream
        .rangeClosed(1, yearMonth.lengthOfMonth())
        .mapToObj(yearMonth::atDay).forEach(this::refreshItem);
    getElement().executeJs("setTimeout(()=>this._clearEmptyDaysStyle())");
  }

  /**
   * Refresh the style of the given date.
   *
   * @param date the date to update the style for
   */
  public void refreshItem(LocalDate date) {
    if (date.getYear() == yearMonth.getYear() && date.getMonth() == yearMonth.getMonth()) {
      String className =
          Optional.ofNullable(classNameGenerator).map(g -> g.apply(date)).orElse(null);
      getElement().executeJs("setTimeout(()=>{this._setStyleForDay($0,$1);})", date.getDayOfMonth(),
          className);
    }
  }

  @Override
  public void setValue(LocalDate value) {
    Objects.requireNonNull(value);
    super.setValue(value);
    setYearMonth(YearMonth.from(value));
  }

  private void setYearMonth(YearMonth value) {
    Objects.requireNonNull(value);
    if (!value.equals(yearMonth)) {
      yearMonth = value;
      refreshAll();
    }
  }

}
