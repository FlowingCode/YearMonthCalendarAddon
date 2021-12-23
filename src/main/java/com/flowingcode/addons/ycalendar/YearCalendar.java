package com.flowingcode.addons.ycalendar;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.shared.Registration;
import java.time.LocalDate;
import java.time.Year;
import java.util.stream.IntStream;

@Tag("fc-year-calendar")
@JsModule("./fc-year-calendar/fc-year-calendar.js")
@SuppressWarnings("serial")
public class YearCalendar extends AbstractCalendarComponent implements HasSize {

  private ValueProvider<LocalDate, String> classNameGenerator;

  /** Updates the displayed year. */
  public void setYear(int year) {
    getElement().setProperty("year", year);
  }

  /** Return the displayed year. */
  public int getYear() {
    return getElement().getProperty("year", Year.now().getValue());
  }

  public void setReadOnly(boolean readOnly) {
    getElement().setAttribute("readonly", readOnly);
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

  /** Refresh all the dates that are currently displayed. */
  @Override
  public void refreshAll() {
    Year year = Year.of(getYear());
    IntStream.rangeClosed(1, year.length()).mapToObj(year::atDay).forEach(this::refreshItem);
  }

  /**
  * If the year is currently displayed, refresh the given date. Otherwise do nothing.
  */
  public void refreshItem(LocalDate date) {
    if (date.getYear() == getYear()) {
      String className;
      if (classNameGenerator != null) {
        className = classNameGenerator.apply(date);
      } else {
        className = null;
      }

      getElement().executeJs("setTimeout(()=>{this._setStyleForDay($0,$1,$2);})",
          date.getDayOfMonth(), date.getMonthValue(), className);
    }
  }


  /**
   * Adds a date click listener. The listener is called when the user selects a given date.
   *
   * @param listener the value change listener, not null
   * @return a registration for the listener
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Registration addDateSelectedListener(
      ComponentEventListener<DateSelectedEvent<? super MonthCalendar>> listener) {
    return ComponentUtil.addListener(this, DateSelectedEvent.class, (ComponentEventListener) listener);
  }

}

