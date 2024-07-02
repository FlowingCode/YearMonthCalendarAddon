/*-
 * #%L
 * Year Month Calendar Add-on
 * %%
 * Copyright (C) 2021 - 2024 Flowing Code
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

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datepicker.DatePicker.DatePickerI18n;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.function.SerializableConsumer;
import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("serial")
public class LocaleSelector extends Composite<RadioButtonGroup<Locale>> {

  private final SerializableConsumer<DatePicker.DatePickerI18n> consumer;

  public LocaleSelector(SerializableConsumer<DatePickerI18n> consumer) {
    this.consumer = consumer;
    getContent().addValueChangeListener(ev -> updateLocale(ev.getValue()));
    getContent().setItems(Locale.ENGLISH, Locale.GERMAN, new Locale("es"));
    getContent().setItemLabelGenerator(locale -> locale.getDisplayLanguage(Locale.ENGLISH));
    getContent().setLabel("Choose Language");
    getContent().setValue(Locale.ENGLISH);
  }

  private void updateLocale(Locale locale) {
    DatePickerI18n i18n = new DatePickerI18n();
    DateFormatSymbols dfs = DateFormatSymbols.getInstance(locale);
    i18n.setMonthNames(List.of(dfs.getMonths()));
    i18n.setWeekdays(List.of(dfs.getWeekdays()).stream().skip(1).toList());
    i18n.setWeekdaysShort(List.of(dfs.getShortWeekdays()).stream().skip(1).toList());

    DayOfWeek firstDayOfWeek = WeekFields.of(locale).getFirstDayOfWeek();
    i18n.setFirstDayOfWeek(firstDayOfWeek.getValue() % 7);

    consumer.accept(i18n);
  }

}
