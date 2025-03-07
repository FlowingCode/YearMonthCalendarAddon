/*-
 * #%L
 * Year Month Calendar Add-on
 * %%
 * Copyright (C) 2021 - 2025 Flowing Code
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

import com.flowingcode.vaadin.addons.DemoLayout;
import com.flowingcode.vaadin.addons.GithubLink;
import com.flowingcode.vaadin.addons.demo.TabbedDemo;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.Route;

@SuppressWarnings("serial")
@ParentLayout(DemoLayout.class)
@Route("year-month-calendar")
@GithubLink("https://github.com/FlowingCode/YearMonthCalendarAddon")
@CssImport("./styles/ycalendar-styles.css")
public class YearMonthCalendarDemoView extends TabbedDemo {

  public YearMonthCalendarDemoView() {
    addDemo(YearDemo.class);
    addDemo(YearI18NDemo.class);
    addDemo(YearReadonlyDemo.class);
    addDemo(MonthDemo.class);
    addDemo(InlineDatePickerDemo.class);
    addDemo(YearMonthFieldDemo.class);
    setSizeFull();
  }

}
