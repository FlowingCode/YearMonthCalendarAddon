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

import {IronA11yKeysBehavior} from '@polymer/iron-a11y-keys-behavior/iron-a11y-keys-behavior.js';
import {ShadowFocusMixin} from '@vaadin/field-base/src/shadow-focus-mixin.js';
import {ThemableMixin} from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';


export class MonthCalendarMixin extends ShadowFocusMixin(ThemableMixin(PolymerElement)) {

  static get properties() {
    return {

      /**
       * A `Date` object defining the month to be displayed. Only year and
       * month properties are actually used.
       */
      month: {
        type: Date,
        value: new Date()
      },

   	  /**
       * A `Date` object for the currently selected date.
       */
      selectedDate: {
        type: Date,
        notify: true
      },

      /**
       * A `Date` object for the currently focused date.
       */
      focusedDate: Date,

      showWeekNumbers: {
        type: Boolean,
        value: false
      },

      i18n: {
        type: Object
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
      }

 	}
  }

  get focusElement() {
    return this.$.element;
  }
  
  _getWeekOfMonth(date) {
	  return Math.floor(this.$.element._days.findIndex(d=>
	    d && 
	  	d.getFullYear() === date.getFullYear() &&
	  	d.getMonth() === date.getMonth() &&
	  	d.getDate() === date.getDate())/7);
  }
  
  _getDaysMatrix() {
	  return [...Array(6).keys()].map(i=>i*7).map(i=>this.$.element._days.slice(i,i+7));
  }
  
  _eventKey(e) {
    var keys = ['down', 'up', 'left', 'right'];

    for (var i = 0; i < keys.length; i++) {
      var k = keys[i];
      if (IronA11yKeysBehavior.keyboardEventMatchesKeys(e, k)) {
        return k;
      }
    }
  }
   
  static get template() {
		return html`
		<vaadin-month-calendar id="element"
              i18n="[[i18n]]"
              month="[[month]]"
              selected-date="{{selectedDate}}"
              focused-date="[[focusedDate]]"
              ignore-taps="[[ignoreTaps]]"
              show-week-numbers="[[showWeekNumbers]]"
              min-date="[[minDate]]"
              max-date="[[maxDate]]"
              focused$="[[_focused]]"
              part="month"
              theme$="[[theme]]"
              on-key-down="_onKeyDown"
            >
            </vaadin-month-calendar>
	`;}
  
  
}

