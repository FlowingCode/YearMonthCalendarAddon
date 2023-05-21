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

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.function.ValueProvider;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.Objects;
import java.util.stream.IntStream;

/** A component for displaying a calendar view of a specific month and year. */
@Tag("fc-month-calendar")
@JsModule("./fc-month-calendar/month-calendar-mixin.js")
@JsModule("./fc-month-calendar/fc-month-calendar.js")
@NpmPackage(value = "@polymer/iron-a11y-keys-behavior", version = "3.0.1")
@Uses(DatePicker.class)
@SuppressWarnings("serial")
public class MonthCalendar extends AbstractCalendarComponent<MonthCalendar> implements HasSize, HasTheme {

  private YearMonth yearMonth;

  private ValueProvider<LocalDate, String> classNameGenerator;

  /**
   * Creates a new instance of {@code MonthCalendar} for the given year and month.
   * 
   * @param year the year (in four digits) to display
   * @param month the month-of-year to display
   */
  public MonthCalendar(int year, Month month) {
    this(YearMonth.of(year, month));
  }

  /**
   * Creates a new instance of {@code MonthCalendar} for the given year and month.
   *
   * @param yearMonth the {@code YearMonth} to display
   */
  public MonthCalendar(YearMonth yearMonth) {
    setYearMonth(yearMonth);
  }

  /**
   * Sets the readonly attribute for the component.
   *
   * @param readOnly the state of the readonly attribute
   */
  public void setReadOnly(boolean readOnly) {
    getElement().setAttribute("readonly", readOnly);
  }

  /**
   * Returns the year and month displayed in this calendar.
   *
   * @return the year and month displayed in this calendar
   */
  public YearMonth getYearMonth() {
    return yearMonth;
  }

  /**
   * Returns the month displayed in this calendar.
   *
   * @return the month displayed in this calendar
   */
  public Month getMonth() {
    return yearMonth.getMonth();
  }

  /**
   * Sets the year and month to display in this calendar.
   * 
   * @param yearMonth the {@code YearMonth} to display
   */
  public void setYearMonth(YearMonth yearMonth) {
    this.yearMonth = Objects.requireNonNull(yearMonth);
    this.yearMonth = Objects.requireNonNull(yearMonth);
    String script = "this.month=new Date($0,$1-1,1);";
    getElement().executeJs(script, yearMonth.getYear(), yearMonth.getMonthValue());
    refreshAll();
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
  @Override
  public void refreshAll() {
    IntStream.rangeClosed(1, yearMonth.lengthOfMonth()).forEach(dayOfMonth -> {
      String className;
      if (classNameGenerator != null) {
        className = classNameGenerator.apply(yearMonth.atDay(dayOfMonth));
      } else {
        className = null;
      }
      setStyleForDay(dayOfMonth, className);
    });
    getElement().executeJs("setTimeout(()=>this._clearEmptyDaysStyle())");
  }

  /**
   * Refresh the style of the given date.
   *
   * @param date the date to update the style for
   * @throws IllegalArgumentException if the displayed year and month does not contain {@code date}
   */
  public void refreshItem(LocalDate date) {
    if (date.getYear() == yearMonth.getYear() && date.getMonth() == yearMonth.getMonth()) {
      String className;
      if (classNameGenerator != null) {
        className = classNameGenerator.apply(date);
      } else {
        className = null;
      }
      setStyleForDay(date.getDayOfMonth(), className);
    } else {
      throw new IllegalArgumentException();
    }
  }

  private void setStyleForDay(int dayOfMonth, String className) {
    getElement().executeJs("setTimeout(()=>{this._setStyleForDay($0,$1);})", dayOfMonth, className);
  }

}
