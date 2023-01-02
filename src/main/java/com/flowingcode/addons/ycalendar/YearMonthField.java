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

import com.vaadin.flow.component.AbstractSinglePropertyField;
import com.vaadin.flow.component.HasTheme;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.function.SerializableFunction;
import java.time.YearMonth;
import java.util.Optional;

@SuppressWarnings("serial")
@Tag("fc-year-month-field")
@JsModule("./fc-year-month-field/fc-year-month-field.js")
public class YearMonthField extends AbstractSinglePropertyField<YearMonthField, YearMonth> implements HasTheme {

  private static final String VALUE_PROPERTY = "value";

  private static <R,S> SerializableFunction<R,S> map(SerializableFunction<R,S> f) {
    return r->Optional.ofNullable(r).map(f).orElse(null);
  }
  
  public <P> YearMonthField() {
    super(VALUE_PROPERTY, null, String.class, map(YearMonth::parse), map(YearMonth::toString));
    setValue(YearMonth.now());
  }  

}
