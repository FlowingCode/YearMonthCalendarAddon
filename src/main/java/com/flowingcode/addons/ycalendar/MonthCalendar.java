/*-
 * #%L
 * Year Month Calendar Add-on
 * %%
 * Copyright (C) 2021 - 2022 Flowing Code
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
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.function.ValueProvider;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.chrono.IsoChronology;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.ValueRange;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Tag("fc-month-calendar")
@JsModule("./fc-month-calendar/month-calendar-mixin.js")
@JsModule("./fc-month-calendar/fc-month-calendar.js")
@Uses(DatePicker.class)
@SuppressWarnings("serial")
public class MonthCalendar extends AbstractCalendarComponent<MonthCalendar> implements HasSize, HasTheme {

  // current yearMonth in ISO Chronology
  // This is NOT the equivalent date in the ISO calendar, but a date in some month with the same
  // "shape" (i.e. a month that starts on the same week-day and has the same number of days).
  // For instance, Dhul Hijjah 1442 is a 29-day month starting on Sunday, 11 July 2021.
  // The corresponding isoYearMonth is February 2004 (a 29-day month starting on Sunday)
  private YearMonth isoYearMonth;

  // current yearMonth in the selected Chronology
  private YearMonth chronoYearMonth;

  // start of the current month in the selected Chronology
  private ChronoLocalDate chronoLocalDate;

  private Chronology chronology = IsoChronology.INSTANCE;

  private ValueProvider<LocalDate, String> classNameGenerator;

  /** Creates a new instance of {@code MonthCalendar} for the given year and month. */
  public MonthCalendar(int year, Month month) {
    this(YearMonth.of(year, month));
  }

  /** Creates a new instance of {@code MonthCalendar} for the given year-and-month. */
  public MonthCalendar(YearMonth yearMonth) {
    setYearMonth(yearMonth);
  }

  /**
   * Creates a new instance of {@code MonthCalendar} for the year, month and calendar system given
   * by a {@code ChronoLocalDate}.
   * <p>
   * This implementation requires that the {@code chronology} has 12-month year and 7-day weeks, and
   * that all months have between 28 and 31 days.
   */
  public MonthCalendar(ChronoLocalDate chronoLocalDate) {
    chronology = chronoLocalDate.getChronology();
    validateChronology(chronology);
    setYearMonth(YearMonth.of(chronoLocalDate.get(ChronoField.YEAR),
        chronoLocalDate.get(ChronoField.MONTH_OF_YEAR)));
  }

  public void setReadOnly(boolean readOnly) {
    getElement().setAttribute("readonly", readOnly);
  }

  /** Returns the year-and-month that is displayed in this calendar. */
  public YearMonth getYearMonth() {
    return chronoYearMonth;
  }

  /** Returns the month-of-year that is displayed in this calendar. */
  public Month getMonth() {
    return chronoYearMonth.getMonth();
  }

  /** Sets the year-and-month that is displayed in this calendar. */
  public void setYearMonth(YearMonth yearMonth) {
    chronoYearMonth = Objects.requireNonNull(yearMonth);

    String script = "this.month=new Date($0,$1-1,1);";
    int year = chronoYearMonth.get(ChronoField.YEAR);
    int month = chronoYearMonth.get(ChronoField.MONTH_OF_YEAR);
    chronoLocalDate = chronology.date(year, month, 1);

    if (chronology instanceof IsoChronology) {
      // for ISO chronology use the default implementation of vaadin-month-calendar
      script = "this._chronology={}; " + script;
      getElement().executeJs(script, year, month);
      isoYearMonth = chronoYearMonth;
    } else {
      isoYearMonth = findIsoYearMonth(chronoLocalDate);

      int isoYear = isoYearMonth.get(ChronoField.YEAR);
      int isoMonth = isoYearMonth.get(ChronoField.MONTH_OF_YEAR);

      script = "this._chronology={year:$2, month:$3}; " + script;
      getElement().executeJs(script, isoYear, isoMonth, year, month);
    }

    refreshAll();
  }

  private static int getDaysInMonth(Temporal t) {
    return t.with(TemporalAdjusters.lastDayOfMonth()).get(ChronoField.DAY_OF_MONTH);
  }

  private static YearMonth findIsoYearMonth(ChronoLocalDate chronoDate) {
    // find a month that starts in the same day of week as the chronoDate
    // and has the same number of days in month

    Integer dayOfWeek = chronoDate.get(ChronoField.DAY_OF_WEEK);
    Integer daysInMonth = (int) chronoDate.range(ChronoField.DAY_OF_MONTH).getMaximum();

    LocalDate date = LocalDate.of(2000, 2, 1);
    if (daysInMonth == 29) {
      while (date.get(ChronoField.DAY_OF_WEEK) != dayOfWeek) {
        date = date.plusYears(4);
      }
    } else {
      while (date.get(ChronoField.DAY_OF_WEEK) != dayOfWeek
          || getDaysInMonth(date) != daysInMonth) {
        date = date.plusMonths(1);
      }
    }
    return YearMonth.from(date);
  }

  /**
   * Sets the function that is used for generating CSS class names for rows in this calendar.
   * Returning {@code null} from the generator results in no custom class name being set. Multiple
   * class names can be returned from the generator as space-separated.
   */
  public void setClassNameGenerator(ValueProvider<LocalDate, String> classNameGenerator) {
    this.classNameGenerator = classNameGenerator;
    refreshAll();
  }

  /**
   * Refresh the styles for all the dates in the displayed year-and-month.
   */
  @Override
  public void refreshAll() {
    IntStream.rangeClosed(1, chronoYearMonth.lengthOfMonth()).forEach(dayOfMonth -> {
      String className;
      if (classNameGenerator != null) {
        className = classNameGenerator
            .apply(LocalDate.from(chronoLocalDate.plus(dayOfMonth - 1, ChronoUnit.DAYS)));
      } else {
        className = null;
      }
      setStyleForDay(dayOfMonth, className);
    });
  }

  /**
   * Refresh the style for the given date.
   *
   * @throws IllegalArgumentException if the displayed year-and-month does not contain {@code date}.
   */
  public void refreshItem(LocalDate date) {
    if (date.getYear() == chronoYearMonth.getYear()
        && date.getMonth() == chronoYearMonth.getMonth()) {
      String className;
      if (classNameGenerator != null) {
        LocalDate isoDate = isoYearMonth.atDay(date.getDayOfMonth());
        className = classNameGenerator.apply(isoDate);
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


  private void validate(Chronology chronology, ChronoField field, Predicate<ValueRange> predicate) {
    ValueRange range = chronology.range(field);
    if (!predicate.test(range)) {
      throw new IllegalArgumentException(
          String.format("Unsupported Chronology %s (%s out of range)", chronology, field));
    }
  }

  private void validateChronology(Chronology chronology) {
    if (chronology != null) {
      // Check that all weeks have 7 days
      validate(chronology, ChronoField.DAY_OF_WEEK, range -> range.getMaximum() == 7);
      validate(chronology, ChronoField.DAY_OF_WEEK, range -> range.getSmallestMaximum() == 7);
      validate(chronology, ChronoField.DAY_OF_WEEK, range -> range.getLargestMinimum() == 1);
      // Check that all years have 12 months
      validate(chronology, ChronoField.MONTH_OF_YEAR, range -> range.getMaximum() == 12);
      validate(chronology, ChronoField.MONTH_OF_YEAR, range -> range.getSmallestMaximum() == 12);
      validate(chronology, ChronoField.MONTH_OF_YEAR, range -> range.getLargestMinimum() == 1);
      // Check that all months have between 28 and 31 days
      validate(chronology, ChronoField.DAY_OF_MONTH, range -> range.getMaximum() <= 31);
      validate(chronology, ChronoField.DAY_OF_MONTH, range -> range.getSmallestMaximum() >= 28);
      validate(chronology, ChronoField.DAY_OF_MONTH, range -> range.getLargestMinimum() == 1);
    }
  }

}
