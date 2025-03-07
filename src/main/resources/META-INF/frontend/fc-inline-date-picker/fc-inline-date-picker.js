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
import { css, html, LitElement } from 'lit';
import {formatDate} from '../fc-year-calendar/fc-calendar-utils.js';
 
export class InlineDatePicker extends LitElement {

  static get is() { return 'fc-inline-date-picker'; }

  static get properties() {
    return {
      value: {type: String, attribute: false},
      date:  {type: Date},      
      displayDate:  {type: Date},
      i18n: {type: Object},
      showWeekNumbers: {type: Boolean}
    };
  }

  constructor() {
    super();
    this.displayDate = new Date();
  }
  
  willUpdate(changedProperties) {
    if (changedProperties.has('value')) {
      this.date = this.value ? new Date(this.value) : undefined;
      if(this.date && this.date.getTimezoneOffset()>0){
    	  // normalize date ignoring browser timezone
          this.date = new Date(this.date.toISOString().slice(0, -1));
      }
    }
    if (changedProperties.has('date')) {
      this.value = this.date ? formatDate(this.date).substring(0,10) : undefined;
      this.displayDate = this.date || this.displayDate;
    }
    if (changedProperties.has('displayDate')) {
      this.displayDate = this.displayDate || new Date();
    }
    if (changedProperties.has('i18n') && this.i18n) {
      [
        this.shadowRoot.querySelector("fc-year-month-field"),
        this.shadowRoot.querySelector("fc-month-calendar")
      ].forEach(e=>{if (e) e.i18n=this.i18n;});
    }
  }

  updated(changedProperties) {
    if (changedProperties.has('value')) {
      this.dispatchEvent(new CustomEvent('value-changed'));
    }
    if (changedProperties.has('displayDate')) {
      this.dispatchEvent(new CustomEvent('display-changed')); 
    }
  }

  static get styles() {
    return css`
      :host {
        display: flex;
        flex-direction: column;
      }
      fc-year-month-field {align-self: center}
      fc-month-calendar {--__month-calendar-header-display: none}
    `;
  }

  render() {
    return html`
      <fc-year-month-field .date="${this.displayDate}" @value-changed="${this.__onDisplayMonthChange}"></fc-year-month-field>
      <fc-month-calendar   .month="${this.displayDate}" @date-selected="${this.__onDateSelected}" 
      	.showWeekNumbers="${this.showWeekNumbers ? this.showWeekNumbers : undefined}"></fc-year-month-field>
    `;
  }

  __onDisplayMonthChange() {
    const yearMonth = this.shadowRoot.querySelector("fc-year-month-field").value;
    this.displayDate=new Date(yearMonth+'-02');
    this.dispatchEvent(new CustomEvent("month-change",{detail:{value: yearMonth}}));
  }

  __onDateSelected(e) {	
      this.value=e.detail.value;
  }

  _setStyleForDay(i,className) { 
    this.shadowRoot.querySelector("fc-month-calendar")._setStyleForDay(i,className);
  }
  
  _clearEmptyDaysStyle() {
    this.shadowRoot.querySelector("fc-month-calendar")._clearEmptyDaysStyle();
  }
}

customElements.define(InlineDatePicker.is, InlineDatePicker);
