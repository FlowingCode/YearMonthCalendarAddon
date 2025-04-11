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
    const buttons=document.createElement('fc-date-picker-buttons');
    buttons.setAttribute('slot','prefix');
    this.appendChild(buttons);
    buttons.addEventListener("click-up", ()=>this._onButtonClick(+1));
    buttons.addEventListener("click-down", ()=>this._onButtonClick(-1));
    super.ready();
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
