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
 
export class YearMonthField extends LitElement {

  static get is() { return 'fc-year-month-field'; }

  static get properties() {
    return {
      value: {type: String, attribute: false},
      date:  {type: Date},
      year:  {type: Number, readOnly: true, state: true},
      month: {type: Number, readOnly: true, state: true},
      i18n:  {type: Object}
    }
  }

  constructor() {
    super();
    this.i18n = {
        formatTitle : (monthName, fullYear) => monthName + ' ' + fullYear,
        monthNames: [
            'January',
            'February',
            'March',
            'April',
            'May',
            'June',
            'July',
            'August',
            'September',
            'October',
            'November',
            'December'
        ],
      };
  }

  willUpdate(changedProperties) {
    if (changedProperties.has('value')) {
      this.date = this.value ? new Date(this.value.substring(0,7)+'-02') : new Date();
    }
    if (changedProperties.has('date')) {
      this.value = this.date.toISOString().substring(0,7);
      this.date.setDate(1);
      this._year  = this.date.getFullYear();
      this._month = this.date.getMonth()+1;
    }
    if (changedProperties.has('i18n') && !this.i18n) {
      this.i18n = changedProperties.get('i18n');	
    }
  }

  updated(changedProperties) {
    if (changedProperties.has('value')) {
      this.dispatchEvent(new CustomEvent('value-changed'));
    }
  }
  
  static get styles() {
    return css`
      :host {
        flex-wrap: nowrap;
        display: flex;
      }
      [part~='header'] {
        align-self: center;
        text-align: center;
        min-width: var(--header-min-width, 120px);
      }
      vaadin-button {
        margin: var(--lumo-space-xs);
      }
    `;
  }

  render() {
    return html`
      <vaadin-button theme="small icon" @click="${this.__decYear}">&lt;&lt;</vaadin-button>
      <vaadin-button theme="small icon" @click="${this.__decMonth}">&lt;</vaadin-button>
      <div part="header">${this.formatTitle(this._month, this._year)}</div>
      <vaadin-button theme="small icon" @click="${this.__incMonth}">&gt;</vaadin-button>
      <vaadin-button theme="small icon" @click="${this.__incYear}">&gt;&gt;</vaadin-button>
    `;
  }
  
  formatTitle(month, fullYear) {
    const monthName = this.i18n.monthNames[month-1];
    return this.i18n.formatTitle(monthName, fullYear);
  }

  __addMonths(delta) {
    const date = new Date(this.date);
    date.setMonth(date.getMonth()+delta);
    this.date = date;
  }

  __decYear() {
    this.__addMonths(-12); 
  }

  __decMonth() {
    this.__addMonths(-1);
  }

  __incYear() {
    this.__addMonths(12); 
  }

  __incMonth() {
    this.__addMonths(1); 
  }
 
}

customElements.define(YearMonthField.is, YearMonthField);
