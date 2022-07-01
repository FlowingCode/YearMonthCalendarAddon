package com.flowingcode.addons.ycalendar;

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.function.SerializableFunction;
import java.time.LocalDate;
import java.util.Optional;

@SuppressWarnings("serial")
@Tag("fc-inline-date-picker")
@JsModule("./fc-inline-date-picker/fc-inline-date-picker.js")
public class InlineDatePicker extends AbstractSinglePropertyField<InlineDatePicker, LocalDate> implements HasSize, HasTheme {

  private static final String VALUE_PROPERTY = "value";
  
  private static <R,S> SerializableFunction<R,S> map(SerializableFunction<R,S> f) {
    return r->Optional.ofNullable(r).map(f).orElse(null);
  }
  
  public InlineDatePicker() {
    super(VALUE_PROPERTY, null, String.class, map(LocalDate::parse), map(LocalDate::toString));
    setValue(LocalDate.now());
  }

}
