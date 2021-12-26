package com.flowingcode.addons.ycalendar;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.internal.JsonSerializer;
import com.vaadin.flow.shared.Registration;
import elemental.json.JsonObject;
import java.util.Objects;

@SuppressWarnings("serial")
public abstract class AbstractCalendarComponent<COMPONENT extends Component> extends Component {

  private DatePickerI18n i18n;

  @Override
  protected void onAttach(AttachEvent attachEvent) {
    if (i18n != null) {
      setI18nWithJS();
    }
    refreshAll();
  }

  public void setI18n(DatePickerI18n i18n) {
    Objects.requireNonNull(i18n, "The I18N properties object should not be null");
    this.i18n = i18n;
    getUI().ifPresent(ui -> setI18nWithJS());
  }

  private void setI18nWithJS() {
    runBeforeClientResponse(ui -> {
      JsonObject i18nObject = (JsonObject) JsonSerializer.toJson(i18n);
      for (String key : i18nObject.keys()) {
        getElement().executeJs("this.set('i18n." + key + "', $0)", i18nObject.get(key));
      }
    });
  }

  private void runBeforeClientResponse(SerializableConsumer<UI> command) {
    getElement().getNode()
        .runWhenAttached(ui -> ui.beforeClientResponse(this, context -> command.accept(ui)));
  }

  public abstract void refreshAll();

  /**
   * Adds a date selection listener. The listener is called when the user selects a given date.
   *
   * @param listener the value change listener, not null
   * @return a registration for the listener
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  public Registration addDateSelectedListener(
      ComponentEventListener<DateSelectedEvent<? super COMPONENT>> listener) {
    return ComponentUtil.addListener(this, DateSelectedEvent.class,
        (ComponentEventListener) listener);
  }

}
