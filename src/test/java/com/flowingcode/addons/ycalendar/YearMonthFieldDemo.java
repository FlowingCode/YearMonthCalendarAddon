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

import com.flowingcode.vaadin.addons.demo.DemoSource;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@DemoSource
@PageTitle("Year-Month Field")
@Route(value = "year-month-calendar/year-month-field", layout = YearMonthCalendarDemoView.class)
public class YearMonthFieldDemo extends Div {

  private static final String FRENCH = "French";
  private static final String SPANISH = "Spanish";
  private static final String ENGLISH = "English";

  public YearMonthFieldDemo() {
    YearMonthField field = new YearMonthField();
    add(field);

    RadioButtonGroup<String> languageSelector = new RadioButtonGroup<>("Choose language:");
    languageSelector.setItems(ENGLISH, SPANISH, FRENCH);
    languageSelector.setValue(ENGLISH);
    languageSelector.addValueChangeListener(e -> {
      DatePickerI18n i18n = new DatePickerI18n();
      switch (e.getValue()) {
        case SPANISH:
          i18n.setMonthNames(List.of("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
              "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"));
          break;
        case FRENCH:
          i18n.setMonthNames(
              List.of("janvier", "février", "mars", "avril", "mai", "juin", "juillet",
                  "août", "septembre", "octobre", "novembre", "décembre"));
          break;
        default:
          // english
      }
      field.setI18n(i18n);
    });

    add(languageSelector);

    Span minRangeValue = new Span("-");
    Span maxRangeValue = new Span("-");
    add(new Div(new Text("Min: "), minRangeValue, new Text(" :: Max: "), maxRangeValue));

    YearMonth min = YearMonth.now().minusYears(2);
    Button setMinRangeButton = new Button("Set min " + min.toString());
    setMinRangeButton.addClickListener(e -> {
      field.setMin(min);
      minRangeValue
          .setText(Optional.ofNullable(field.getMin()).map(YearMonth::toString).orElse("-"));
    });

    YearMonth max = YearMonth.now().plusYears(2);
    Button setMaxRangeButton = new Button("Set max " + max.toString());
    setMaxRangeButton.addClickListener(e -> {
      field.setMax(max);
      maxRangeValue
          .setText(Optional.ofNullable(field.getMax()).map(YearMonth::toString).orElse("-"));
    });

    Button clearRangeButton = new Button("Clear range");
    clearRangeButton.addClickListener(e -> {
      field.setMin(null);
      field.setMax(null);
      minRangeValue.setText("-");
      maxRangeValue.setText("-");
    });

    YearMonth newValue = YearMonth.now().plusYears(3);
    Button setValueButton = new Button("Set value " + newValue.toString());
    setValueButton.addClickListener(e -> field.setValue(newValue));

    add(new HorizontalLayout(setMinRangeButton, setMaxRangeButton, clearRangeButton,
        setValueButton));
  }

}

