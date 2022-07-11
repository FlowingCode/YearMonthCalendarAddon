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

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DebounceSettings;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import com.vaadin.flow.dom.DebouncePhase;
import java.time.LocalDate;
import java.util.Objects;

@DomEvent(value = "date-selected",
    debounce = @DebounceSettings(timeout = 200, phases = DebouncePhase.TRAILING))
public class DateSelectedEvent<T extends Component> extends ComponentEvent<T> {

  private static final long serialVersionUID = 1L;

  private LocalDate date;

  public DateSelectedEvent(T source, boolean fromClient,
      @EventData("event.detail.value") String date) {
    this(source, fromClient, LocalDate.parse(date));
  }

  public DateSelectedEvent(T source, boolean fromClient, LocalDate date) {
    super(source, fromClient);
    this.date = Objects.requireNonNull(date);
  }

  public LocalDate getDate() {
    return date;
  }

}
