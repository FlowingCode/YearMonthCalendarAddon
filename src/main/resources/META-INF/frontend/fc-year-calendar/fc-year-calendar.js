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
import {ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';
import {} from '@polymer/polymer/lib/elements/dom-repeat.js';
import {} from '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import {} from '../fc-month-calendar/fc-month-calendar.js';

import {formatDate} from '../fc-year-calendar/fc-calendar-utils.js';

export class FcYearCalendarElement extends ThemableMixin(PolymerElement) {
  static get is() { return 'fc-year-calendar'; }

  static get properties() {
    return {
      /**
       * Flag stating whether the component is in readonly mode.
       */
      readonly: {
        type: Boolean,
        reflectToAttribute: true
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

      /**
       * A `Date` object defining the month to be displayed. Only year and
       * month properties are actually used.
       */
      year: {
        type: Number,
        value: new Date().getFullYear()
      },

      /**
       * A `Date` object for the currently selected date.
       */
      selectedDate: {
        type: Date,
        notify: true
      },

      showWeekNumbers: {
        type: Boolean,
        value: false
      },
      
      /**
       * Flag stating whether taps on the component should be ignored.
       */
      ignoreTaps: Boolean,

      /**
       * Flag stating whether taps on the component should be ignored.
       */

      disabled: {
        type: Boolean,
        reflectToAttribute: true,
      },
      
      __months: {
       type: Array,
       computed: '__getMonths(year)'
      }
    }
  }
    
  static get template() {
    return html`                
        <style>
            
        </style>
        <vaadin-form-layout id="formLayout">
            <dom-repeat items="[[__months]]" as="month">
                <template>
                  <fc-month-calendar part="month"
                  readonly="[[readonly]]"
                  i18n="[[i18n]]"
                  month="[[month]]"
                  selected-date="{{selectedDate}}"
                  focused-date="[[focusedDate]]"
                  ignore-taps="[[ignoreTaps]]"
                  show-week-numbers="[[showWeekNumbers]]"
                  min-date="[[minDate]]"
                  max-date="[[maxDate]]"
                  focused$="[[_focused]]"
                  theme$="[[theme]]"
                  on-keydown="_onKeyDown"
                >
                </fc-month-calendar>
                </template>
            </dom-repeat>    
        </vaadin-form-layout>
    `;
  }

  ready() {
      super.ready();
      this.$.formLayout.responsiveSteps = [
      {minWidth: 0, columns: 1},
      {minWidth: '22em', columns: 2},
      {minWidth: '33em', columns: 3},
      {minWidth: '44em', columns: 4},
      {minWidth: '66em', columns: 6},
      {minWidth: '132em', columns: 12}      
    ];
  }
  
  
  connectedCallback() {
    super.connectedCallback();
    this.addEventListener("selected-date-changed",this._onSelectedDateChanged);    
  }
    
  disconnectedCallback() {
	this.removeEventListener("selected-date-changed",this._onSelectedDateChanged);
    super.disconnectedCallback();
  }
  
  __getMonths() {
      return Array.apply(0,Array(12)).map((e,month)=>new Date(this.year,month,1));
  }
        
  _setStyleForDay(dayOfMonth,monthOfYear,className) {
    var calendar = this.shadowRoot.querySelectorAll("[part='month']")[monthOfYear-1];
    calendar._setStyleForDay(dayOfMonth, className);
  }
  
  _onKeyDown(ev) {  
  
    const isMonthCalendar = elem => elem.tagName.toLowerCase()=="fc-month-calendar";
    let path = ev.path || (ev.composedPath && ev.composedPath());
    
    if (!path || path.length<3 || !isMonthCalendar(path[2])) {    
        return;
    }
    
    const monthCalendar = path[2];    
    const key = monthCalendar._eventKey(ev);
    if (!key) return;
    
    const adjustDate = (date, delta) => {
        return new Date(date.getFullYear(), date.getMonth(), date.getDate()+delta);
    };
  
  
    const monthCalendars = () => Array.from(this.$.formLayout.children).filter(isMonthCalendar);
    
    const getNumPerRow = () => {
      //https://stackoverflow.com/a/49090306/1297272
      //https://stackoverflow.com/users/438581/brett-dewoody
      const children   = monthCalendars();
      const baseOffset = children[0].offsetTop;
      const breakIndex = children.findIndex(item => item.offsetTop > baseOffset);
      return (breakIndex === -1 ? grid.length : breakIndex);       
    };
    
    const adjustDateByHorizontalOffset = (offset) => {    
      //find the month in the offset calendar on left/right
      const numPerRow = getNumPerRow(); 
      let   month = this.selectedDate.getMonth();
      const row   = Math.floor(month/numPerRow);
      const col   = month%numPerRow + offset;
      month = col+row*numPerRow;
            
      let d = this.selectedDate;      
      if (col>=0 && col<numPerRow) {
        let weekOfMonth = monthCalendars()[d.getMonth()]._getWeekOfMonth(d);
        const days = monthCalendars()[month]._getDaysMatrix();
                                
        if (offset>0) {
           //select the first day in the same week of month
           return days[weekOfMonth].find(day=>!!day) || d;
        } else {
           //select the last day in the same week of month
           return [... days[weekOfMonth]].reverse().find(day=>!!day) || d;           
        }
      }      
      return d;
    }
        
    const adjustDateByVerticalOffset = (offset) => {
      //find the month in the offset calendar above/below
      const numPerRow = getNumPerRow(); 
      let   month = this.selectedDate.getMonth();
      const row   = Math.floor(month/numPerRow) + offset;
      const col   = month%numPerRow;
      month = col+row*numPerRow;
            
      let d = this.selectedDate;
      if (month>=0 && month<12) {
        let dayOfWeek = d.getDay();
        if (offset>0) {
          //select the first day in month with the same date of week
          d = new Date(d.getFullYear(), month, 1);
          while (d.getDay()!=dayOfWeek) d = adjustDate(d, +1);
        } else {
          //select the last day in month with the same date of week
          d = new Date(d.getFullYear(), month+1, 0);
          while (d.getDay()!=dayOfWeek) d = adjustDate(d, -1);
        }
      } 
      return d;
    };
    
    if (ev.ctrlKey) {
      let dayOfWeek = this.selectedDate.getDay();
      let month = this.selectedDate.getMonth();
        
      switch (key) {            
        case 'left': {
            let firstDayOfWeek = this.i18n.firstDayOfWeek;
            let dayOfMonth = this.selectedDate.getDate();
            if (dayOfWeek==firstDayOfWeek || dayOfMonth==1) {                
                this.selectedDate = adjustDateByHorizontalOffset(-1);
                return;
            }
            break;
        }
        case 'right': {
            let lastDayOfWeek  = (this.i18n.firstDayOfWeek-1+7)%7;
            let dayOfMonth = this.selectedDate.getDate();
            let lastDayOfMonth = new Date(this.year, this.selectedDate.getMonth()+1, 0).getDate();
            if (dayOfWeek==lastDayOfWeek || dayOfMonth==lastDayOfMonth) {                                    
                this.selectedDate = adjustDateByHorizontalOffset(+1);
                return;
            }
            break;
        }
        case 'up': {                
            if (month != adjustDate(this.selectedDate, -7).getMonth()) {
                this.selectedDate = adjustDateByVerticalOffset(-1);                    
                return;
            }
            break;
        }
        case 'down': {
            if (month != adjustDate(this.selectedDate, +7).getMonth()) {
               this.selectedDate = adjustDateByVerticalOffset(+1);                    
               return;
           }
           break;
        }
      }
    }
    
    let delta=0;
    switch (key) {
      case 'left':  delta=-1; break;
      case 'right': delta=+1; break;
      case 'up':    delta=-7; break;
      case 'down':  delta=+7; break;
    }    
    
    let newDate = adjustDate(this.selectedDate, delta);
    if (newDate.getFullYear()==this.year) { 
      ev.stopPropagation();
      this.selectedDate = newDate;
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

customElements.define(FcYearCalendarElement.is, FcYearCalendarElement);
