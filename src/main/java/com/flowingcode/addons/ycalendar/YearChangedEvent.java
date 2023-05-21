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

/**
 * An event that is triggered when the year value changes in a {@link YearCalendar}.
 *
 * @param <T> the source component type
 */
@DomEvent(value = "year-changed",
    debounce = @DebounceSettings(timeout = 200, phases = DebouncePhase.TRAILING))
public class YearChangedEvent<T extends Component> extends ComponentEvent<T> {

  private static final long serialVersionUID = 1L;

  /** The year of the event. */
  private int year;

  /**
   * Constructs an event with the given {@code source}, {@code boolean}, and {@code year}.
   *
   * @param source the source component
   * @param fromClient whether the event originated from the client-side or the server-side
   * @param year the year value of the event
   */
  public YearChangedEvent(T source, boolean fromClient,
      @EventData("event.detail.value") String year) {
    this(source, fromClient, Integer.parseInt(year));
  }

  /**
   * Constructs an event with the given {@code source}, {@code boolean}, and {@code year}.
   *
   * @param source the source component
   * @param fromClient whether the event originated from the client-side or the server-side
   * @param year the year value of the event
   */
  public YearChangedEvent(T source, boolean fromClient, int year) {
    super(source, fromClient);
    this.year = year;
  }

  /**
   * Gets the year of the event.
   *
   * @return the year of the event
   */
  public int getYear() {
    return year;
  }

}
