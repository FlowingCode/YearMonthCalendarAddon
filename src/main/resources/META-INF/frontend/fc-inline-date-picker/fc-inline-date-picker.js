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
import { css, html, LitElement } from 'lit';
 
export class InlineDatePicker extends LitElement {

  static get is() { return 'fc-inline-date-picker'; }

  static get properties() {
    return {
      value: {type: String, attribute: false},
      date:  {type: Date},      
      displayDate:  {type: Date},
      i18n: {type: Object},
    };
  }

  willUpdate(changedProperties) {
    if (changedProperties.has('value')) {
      this.date = this.value ? new Date(this.value) : undefined;;
    }
    if (changedProperties.has('date')) {
      this.value = this.date ? this.date.toISOString().substring(0,10) : undefined;
      this.displayDate = this.date || this.displayDate;
    }
    if (changedProperties.has('displayDate')) {
      this.displayDate = this.displayDate || new Date();
    }
    if (changedProperties.has('i18n') && this.i18n) {
      this.shadowRoot.querySelector("fc-year-month-field").i18n=this.i18n;
      this.shadowRoot.querySelector("fc-month-calendar").i18n=this.i18n;
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
      <fc-month-calendar   .month="${this.displayDate}" @date-selected="${this.__onDateSelected}"></fc-year-month-field>
    `;
  }

  __onDisplayMonthChange() {
    this.displayDate=new Date(this.shadowRoot.querySelector("fc-year-month-field").value+'-02');
  }

  __onDateSelected(e) {	
      this.value=e.detail.value;
  }

}

customElements.define(InlineDatePicker.is, InlineDatePicker);
