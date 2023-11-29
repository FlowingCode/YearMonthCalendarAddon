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
import { css, html, LitElement } from 'lit';

export class YearMonthField extends LitElement {

  static get is() { return 'fc-year-month-field'; }

  static get properties() {
    return {
      value: {type: String, attribute: false},
      date:  {type: Date},
      year:  {type: Number, readOnly: true, state: true},
      month: {type: Number, readOnly: true, state: true},
      min:   {type: Object, readOnly: true},
	  max:   {type: Object, readOnly: true},
      i18n:  {type: Object}
    }
  }

  constructor() {
    super();
    
    this._i18n = {};
    this.__setDefaultFormatTitle(this._i18n);
	this.__setDefaultMonthNames(this._i18n);
	this._decMonthDisabled = false;
	this._incMonthDisabled = false;
	this._decYearDisabled = false;
	this._incYearDisabled = false;
  }
  
  set i18n(value) {
    let oldValue = this._i18n;
    this._i18n = value;
	  
    if(!this._i18n.formatTitle){
      this.__setDefaultFormatTitle(this._i18n);
    }
    if(!this._i18n.monthNames){
      this.__setDefaultMonthNames(this._i18n);
    }
    this.requestUpdate('i18n', oldValue);
  }
  
  get i18n() { return this._i18n; }

  /**
   * Checks if value is between min and max (inclusive).
   */
  __checkRange(value) {
	return (!this.min || value >= this._minAsNumber) 
	  && (!this.max || value <= this._maxAsNumber);
  }

  willUpdate(changedProperties) {
    if (changedProperties.has('value')) {
	  this.date = this.value ? new Date(this.value.substring(0,7)+'-02') : new Date();
    }
    if (changedProperties.has('i18n') && !this.i18n) {
      this.i18n = changedProperties.get('i18n');	
    }
    if (changedProperties.has('date') || changedProperties.has('min') || changedProperties.has('max')) {
	  const strValue = this.date.toISOString().substring(0,7);
	  this.__normalizeValue(strValue);
      this.date.setDate(1);
      this._year  = this.date.getFullYear();
      this._month = this.date.getMonth() + 1;
      this.__toggleButtons(this.min, this.max);
    }
  }
  
  __normalizeValue(value) {
	  const intValue = parseInt(this.__yearMonthAsNumber(this.date.getFullYear(), this.date.getMonth()));
	  if (this.min && intValue < this._minAsNumber){
		  this.value = this.min.year + '-' + String(this.min.month + 1).padStart(2, '0');
		  this.date = new Date(this.min.year, this.min.month);
	  } if (this.max && intValue > this._maxAsNumber){
          this.value = this.max.year + '-' + String(this.max.month + 1).padStart(2, '0');
          this.date = new Date(this.max.year, this.max.month);
	  } else {
		  this.value = value;
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
      <vaadin-button id="dec-year-button"theme="small icon" @click="${this.__decYear}" ?disabled="${this._decYearDisabled}">&lt;&lt;</vaadin-button>
      <vaadin-button id="dec-month-button" theme="small icon" @click="${this.__decMonth}" ?disabled="${this._decMonthDisabled}">&lt;</vaadin-button>
      <div part="header">${this.formatTitle(this._month, this._year)}</div>
      <vaadin-button id="inc-month-button" theme="small icon" @click="${this.__incMonth}" ?disabled="${this._incMonthDisabled}">&gt;</vaadin-button>
      <vaadin-button id="inc-year-button" theme="small icon" @click="${this.__incYear}" ?disabled="${this._incYearDisabled}">&gt;&gt;</vaadin-button>
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
	if(!this._minAsNumber || this._minAsNumber < this.__dateAsNumber()){
      this.__addMonths(-12);
    }
  }

  __decMonth() {
	if(!this._minAsNumber || this._minAsNumber < this.__dateAsNumber()){
      this.__addMonths(-1);
    }
  }

  __incYear() {
	if(!this._maxAsNumber || this._maxAsNumber > this.__dateAsNumber()){
      this.__addMonths(12);
    }
  }

  __incMonth() {
	if(!this._maxAsNumber || this._maxAsNumber > this.__dateAsNumber()){
      this.__addMonths(1); 
    }
  }

  __setDefaultFormatTitle(obj){
    obj.formatTitle = (monthName, fullYear) => monthName + ' ' + fullYear;
  }

  __setDefaultMonthNames(obj){
    obj.monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
  }
  
  /**
   * Returns a number joining year and month. Month is left padded with zero up to two chars length.
   */
  __yearMonthAsNumber(year, month) {
	  return parseInt(year + '' + String(month).padStart(2, '0'));
  }
  
  /**
   * Converts this.date to yearMonth number
   */
  __dateAsNumber() {
	  return this.date ? this.__yearMonthAsNumber(this.date.getFullYear(), this.date.getMonth()) : undefined;
  }
  
  /**
   * Converts min or max value to yearMonth number. 
   */
  __minMaxAsNumber(value) {
    return value ? this.__yearMonthAsNumber(value.year, value.month) : undefined;
  }
  
  /** 
   * Converts this.min to number
   * @private 
   */
  get _minAsNumber() {
    return this.__minMaxAsNumber(this.min);
  }
      		 
  /** 
   * Converts this.max to number
   * @private 
   */
  get _maxAsNumber() {
    return this.__minMaxAsNumber(this.max);
  }
  
  /**
   * Returns true if delta between minOrMax and this.date is less than 1 year (12 months) 
   */
  __dateDeltaLtYear(minOrMax) {
    const toMonths = (year, month) => year * 12 + month;	  
	const dateToMonths = toMonths(this.date.getFullYear(), this.date.getMonth());
	return Math.abs(dateToMonths - toMonths(minOrMax.year, minOrMax.month)) < 12;
  }
  
  /**
   * Enable or disabled navigation buttons
   */
  __toggleButtons(min, max) {
	const minAsNumber = this.__minMaxAsNumber(min);
	const maxAsNumber = this.__minMaxAsNumber(max);
	
	this._decMonthDisabled = minAsNumber && minAsNumber >= this.__dateAsNumber();
	this._incMonthDisabled = maxAsNumber && maxAsNumber <= this.__dateAsNumber();
	this._decYearDisabled = minAsNumber && (minAsNumber >= this.__dateAsNumber() || this.__dateDeltaLtYear(min));
	this._incYearDisabled = maxAsNumber && (maxAsNumber <= this.__dateAsNumber() || this.__dateDeltaLtYear(max));
  }

}

customElements.define(YearMonthField.is, YearMonthField);
