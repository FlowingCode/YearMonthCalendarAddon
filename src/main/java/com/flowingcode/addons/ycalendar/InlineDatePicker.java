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

import com.sun.org.apache.xpath.internal.operations.Bool;
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
    private static boolean weekNumbersVisible = false;

    private static <R, S> SerializableFunction<R, S> map(SerializableFunction<R, S> f) {
        return r -> Optional.ofNullable(r).map(f).orElse(null);
    }

    public InlineDatePicker() {
        super(VALUE_PROPERTY, null, String.class, map(LocalDate::parse), map(LocalDate::toString));
        setValue(LocalDate.now());
    }

    public void setWeekNumbersVisible(Boolean showWeekNumbers) {
        if (showWeekNumbers) {
            weekNumbersVisible = true;
        }
    }

}
