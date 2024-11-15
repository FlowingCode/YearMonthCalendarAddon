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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DebounceSettings;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.dom.DebouncePhase;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@DomEvent(value = "date-selected",
    debounce = @DebounceSettings(timeout = 200, phases = DebouncePhase.TRAILING))
public class DateSelectedEvent<T extends Component> extends ComponentEvent<T> {

  private static final long serialVersionUID = 1L;

  private LocalDate date;

  /**
   * Creates a new instance of DateSelectedEvent with a specified source component and date string.
   *
   * @param source       the source component
   * @param fromClient   true if the event originated from the client, false otherwise
   * @param date         a String representation of the date
   * @throws DateTimeParseException if date argument cannot be parsed into a valid LocalDate
   */
  public DateSelectedEvent(T source, boolean fromClient,
      @EventData("event.detail.value") String date) {
    this(source, fromClient, LocalDate.parse(date));
  }

  /**
   * Creates a new instance of DateSelectedEvent with a specified source component and LocalDate.
   *
   * @param source       the source component
   * @param fromClient   true if the event originated from the client, false otherwise
   * @param date         the selected date
   * @throws NullPointerException if the LocalDate argument is null
   */
  public DateSelectedEvent(T source, boolean fromClient, LocalDate date) {
    super(source, fromClient);
    this.date = Objects.requireNonNull(date);
  }

  /**
   * Retrieves the selected date associated with the event.
   *
   * @return LocalDate retrieved from this event
   */
  public LocalDate getDate() {
    return date;
  }

}
