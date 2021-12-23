package com.flowingcode.addons.ycalendar;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.DomEvent;
import com.vaadin.flow.component.EventData;
import java.time.LocalDate;
import java.util.Objects;

@DomEvent("date-selected")
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
