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
import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';

import {MonthCalendarMixin} from './month-calendar-mixin.js'
import {formatDate} from '../fc-year-calendar/fc-calendar-utils.js';

export class FcMonthCalendarElement extends MonthCalendarMixin {
  static get is() { return 'fc-month-calendar'; }

  static get properties() {
    return {
      /**
       * Flag stating whether the component is in readonly mode.
       */
      readonly: {
        type: Boolean,
        reflectToAttribute: true,
      },
      
      /**
        * The object used to localize this component.
        * To change the default localization, replace the entire
        * _i18n_ object or just the property you want to modify.
        */ 
      i18n: {
          type: Object,
          value: () => {
            return {
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
              weekdays: ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'],
              weekdaysShort: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
              firstDayOfWeek: 0,
              week: 'Week',
              calendar: 'Calendar',
              clear: 'Clear',
              today: 'Today',
              cancel: 'Cancel',
              formatTitle : (monthName, fullYear) => monthName + ' ' + fullYear,
              formatDate :  (dateObject) => new Date(date.year, date.month-1, date.day).toISOString().replace(/T.*/,'')
            }
          }
     },
      

    }
  }
    
  static get template() {
        return html`
        <style>
            :host([readonly]) {
              pointer-events: none;
            }
            :host { 
                min-width: 11em;
                padding: 0;
                margin: 0;
                padding-top: 1ex;
                --lumo-font-size-l: 11px;
                --lumo-font-size-xs: 10px;
                --lumo-font-size-m: 11px;
                font-size: var(--lumo-font-size-m);
            }
            vaadin-month-calendar:focus { 
                outline: none;
            } 
        </style>
        ${super.template}
    `;}
  
  _setStyleForDay(i,className) { 
    let e = this.$.element.shadowRoot.querySelectorAll("[part='date']:not(:empty)")[i-1];
    if (className) {
        e.className=className;
    } else {
        e.removeAttribute('class');
    }
  }

  ready() {
    super.ready();
    let styles = `
        [part='date'][class]::before { 
          box-shadow: none;
        }
        
        [part='date'][selected] {
          color: unset;
        }
        
        [part='date'][selected]::before {
          background-color: unset;  
          border: 1px solid var(--lumo-primary-color);
        }

        [part~=month-header] {
          display: var(--__month-calendar-header-display, block);
        }
    `;
        
    this.$.element.shadowRoot.querySelector("style").innerHTML+=styles;
  }

  connectedCallback() {
    super.connectedCallback();
    this.addEventListener("selected-date-changed",this._onSelectedDateChanged);    
  }
    
  disconnectedCallback() {
	this.removeEventListener("selected-date-changed",this._onSelectedDateChanged);
    super.disconnectedCallback();
  }

  _onKeyDown(ev) {
    if (ev.model) return;
    if (ev.ctrlKey) return;
    let delta = 0;

    switch (this._eventKey(ev)) {
        case 'left':  delta=-1; break;
        case 'right': delta=+1; break;
        case 'up':    delta=-7; break;
        case 'down':  delta=+7; break;
    }    
    
    if (delta) {
      ev.preventDefault();
      let d = this.selectedDate;
      if (d) {
        let newDate = new Date(d.getFullYear(), d.getMonth(), d.getDate()+delta);
        if (newDate.getMonth()==this.selectedDate.getMonth()) {
        	ev.stopPropagation();
            this.selectedDate = newDate;
        }
      }
    }
  }
  
  _onSelectedDateChanged(ev) {    
    if (ev.detail.value) {
      this.dispatchEvent(new CustomEvent("date-selected", {
        detail: {  value: formatDate(ev.detail.value).substring(0,10) }
      }));
    }
  }
        
}

customElements.define(FcMonthCalendarElement.is, FcMonthCalendarElement);
