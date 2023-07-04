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
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import java.util.List;

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
  }

}

