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

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;
import java.time.LocalDate;
import java.time.Year;
import java.util.Optional;
import java.util.stream.IntStream;

/** A component that displays the calendar of a whole year. */
@Tag("fc-year-calendar")
@JsModule("./fc-year-calendar/fc-year-calendar.js")
@SuppressWarnings("serial")
@Uses(MonthCalendar.class)
public class YearCalendar extends AbstractCalendarComponent<YearCalendar> implements HasSize, HasTheme {
  
  private ValueProvider<LocalDate, String> classNameGenerator;

  /**
   * Updates the displayed year.
   *
   * @param year The year to set
   */
  public void setYear(int year) {
    int currentYear = getYear();
    getElement().setProperty("year", year);
    if (currentYear != year) {
      refreshAll();
    }
  }

  /**
   * Return the displayed year.
   *
   * @return The displayed year
   */
  public int getYear() {
    return getElement().getProperty("year", Year.now().getValue());
  }

  /**
   * Sets the read-only mode.
   *
   * @param readOnly Whether the calendar is set to be read-only
   */
  public void setReadOnly(boolean readOnly) {
    getElement().setAttribute("readonly", readOnly);
  }

  /**
   * Sets the function that generates CSS class names for days in this calendar.
   * Returning {@code null} from the generator results in no custom class name being set. Multiple
   * class names can be returned from the generator as space-separated.
   *
   * @param classNameGenerator The function that generates CSS class names, can be null
   */
  public void setClassNameGenerator(ValueProvider<LocalDate, String> classNameGenerator) {
    this.classNameGenerator = classNameGenerator;
    refreshAll();
  }

  /** Refresh all the dates that are currently displayed. */
  @Override
  public void refreshAll() {
    Year year = Year.of(getYear());
    IntStream.rangeClosed(1, year.length()).mapToObj(year::atDay).forEach(this::refreshItem);
  }

  /** If the year is currently displayed, refresh the given date. Otherwise do nothing. */
  public void refreshItem(LocalDate date) {
    if (date.getYear() == getYear()) {
      String className =
          Optional.ofNullable(classNameGenerator).map(g -> g.apply(date)).orElse(null);
      getElement().executeJs("setTimeout(()=>{this._setStyleForDay($0,$1,$2);})",
          date.getDayOfMonth(), date.getMonthValue(), className);
    }
  }

  /**
   * Adds a year value change listener. The listener is called when the year value changes.
   *
   * @param listener The handler for the year changed event, not null
   * @return a registration for the listener
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Registration addYearChangedListener(
      ComponentEventListener<YearChangedEvent<YearCalendar>> listener) {
    return ComponentUtil.addListener(this, YearChangedEvent.class,
        (ComponentEventListener) listener);
  }
  
}

