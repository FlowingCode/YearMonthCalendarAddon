/*-
 * #%L
 * XTerm Console Addon
 * %%
 * Copyright (C) 2020 - 2021 Flowing Code
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
  
  __getMonths() {
	  return Array.apply(0,Array(12)).map((e,month)=>new Date(this.year,month,1));
  }
		
  _setStyleForDay(dayOfMonth,monthOfYear,className) {
	var calendar = this.shadowRoot.querySelectorAll("[part='month']")[monthOfYear-1];
	calendar._setStyleForDay(dayOfMonth, className);
  }

}

customElements.define(FcYearCalendarElement.is, FcYearCalendarElement);
