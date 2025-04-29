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
import {DatePicker} from '@vaadin/date-picker/vaadin-date-picker.js';
import { html } from '@polymer/polymer/polymer-element.js';
import { defineCustomElement } from '@vaadin/component-base/src/define.js';
import './fc-date-picker-buttons.js';

export class FcDatePicker extends DatePicker {
  static get is() {
    return 'fc-date-picker';
  }
  
  static get template() {
    return html`
        ${super.template}
        <style>
            vaadin-input-container {
                padding-left: 0;
            }
            ::slotted(input) {
                padding-left: calc(0.575em + var(--lumo-border-radius-m) / 4 - 1px);
            }
        </style>
    `;
  }
  
  ready() {
    super.ready();
    
    const buttons=document.createElement('fc-date-picker-buttons');
    buttons.setAttribute('slot','prefix');
    this.appendChild(buttons);
    buttons.addEventListener("click-up", ()=>this._onButtonClick(+1));
    buttons.addEventListener("click-down", ()=>this._onButtonClick(-1));
    
    this._styles={};
    
    this._overlayElement.renderer = e => {
        this._boundOverlayRenderer.call(this,e);
        
        if (!this._overlayContent._monthScroller.__fcWrapped) {
            const createElement = this._overlayContent._monthScroller._createElement;
            this._overlayContent._monthScroller.__fcWrapped = true;
            this._overlayContent._monthScroller._createElement = () => {
                var calendar = createElement();
                calendar.addEventListener('dom-change',ev=>{
                    if (ev.composedPath()[0].as=='week') {
                        setTimeout(()=> this._updateMonthStyles(calendar));
                    }
                });
                return calendar; 
            }
        }
    };
  }
  
  refreshAll() {
    this._styles = {};
    if (this._overlayContent) {
        this._overlayContent._monthScroller.querySelectorAll("vaadin-month-calendar").forEach(calendar=>this._updateMonthStyles(calendar));
    }
  }
  
  _updateMonthStyles(element) {
    
    const _clearStyles = function() { 
        let e = this.shadowRoot.querySelectorAll("[part~='date']");
        e.forEach(item => item.removeAttribute('class'));
    };
 
    const _setStyleForDay = function(i,className) {
        let e = this.shadowRoot.querySelectorAll("[part~='date']:not(:empty)")[i-1];
        if (className) {
            e.className=className;
        } else {
            e.removeAttribute('class');
        }
    };
 
    const _getStyle = month => {
        const tostr = date => date.toISOString().substr(0,7);
        const add   = (date, delta) => new Date(date.getFullYear(), date.getMonth()+delta, 1);
        
        const key = tostr(month);
        
        if (!this._styles[key]) {
            let min = month;
            let max = month;
            const N = 50;
            for (let i=0;i<N;i++) {
                min=add(min,-1);
                if(this._styles[tostr(min)]) {min=add(min,+1); break;}
            }
            for (let i=0;i<N;i++) {
                max=add(max,+1);
                if(this._styles[tostr(max)]) {max=add(max,-1); break;}
            }
            
            const fetched = this.$server.fetchStyles(tostr(min), tostr(max)).catch(err => {
                console.error('fetchStyles '+tostr(min)+' '+tostr(max)+' failed', err);
                return {};
            });
            for (let m=min;m<=max;m=add(m,1)) {
                const k = tostr(m);
                this._styles[k] = new Promise(resolve => {
                    fetched.then(styles=>resolve(styles[k] || {}));
                });
            }
        }
        return this._styles[key];
    }
 
    const month = element.month;
    _clearStyles.call(element);
    _getStyle(month).then(styles=>setTimeout(()=>{
        if (element.month===month) {
            for (let i in styles) {
                _setStyleForDay.call(element,i,styles[i]);
            }
        }
    }));
  }
  
  _onButtonClick(delta) {
    // If input element contains a valid date
    const date = this.__parseDate(this.inputElement.value);
    if (date) {
      
      // Count how many non-digit characters appear before the current cursor position
      let index=0;
      for (let i=0;i<this.inputElement.selectionStart;i++) {
        if (!this.inputElement.value[i].match(/[0-9]/)) index++;
      }
      
      // Map the component index (e.g. 0 for first, 1 for second), 
      // to its corresponding date part character ('M', 'D', or 'Y')
      // based on the formatted structure.
      const format = this.__formatDate(new Date(3333,10,22))
                           .replaceAll('1','M')
                           .replaceAll('2','D')
                           .replaceAll('3','Y');
      const part = format.replace(new RegExp(`^([DMY]+[^DMY]){${index}}`),'')[0];
      
      const incrementComponent = (date, part, delta) => {
          // Adjusts the specified component (year, month, or day) by the given delta,
          // and returns the clamped date in ISO format.
          const d = new Date(date);
          switch (part) {
              case 'Y': d.setFullYear(d.getFullYear() + delta); break;
              case 'M': d.setMonth   (d.getMonth()    + delta); break;
              case 'D': d.setDate    (d.getDate()     + delta); break;
           }
           return this._formatISO(d);
      };
      
      this.value = incrementComponent(date, part, delta);
      
      // Find the start of the index-th component
      let start=0;
      for (let i=0;i<index;start++) {
        if (!this.inputElement.value[start].match(/[0-9]/)) i++;
      }
      
      // Find the end of the index-th component
      let end=start;
      while (end<this.inputElement.value.length && this.inputElement.value[end].match(/[0-9]/)) {
        end++;
      }
      
      this.inputElement.selectionStart=start;
      this.inputElement.selectionEnd=end;
      
      this.inputElement.focus();
    }
  }
  
}

defineCustomElement(FcDatePicker);
