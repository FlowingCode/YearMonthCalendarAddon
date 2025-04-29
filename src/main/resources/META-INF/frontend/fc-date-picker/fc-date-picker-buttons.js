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
import { defineCustomElement } from '@vaadin/component-base/src/define.js';
import { ThemableMixin } from '@vaadin/vaadin-themable-mixin/vaadin-themable-mixin.js';

export class FcDatePickerButtons extends ThemableMixin(LitElement) {
  static get is() {
    return 'fc-date-picker-buttons';
  }
  render() {
    return html`
          <button id="up"
                  type="button"
                  aria-label="Increment"
                  @click=${this._clickUp}></button>
          <button id="down"
                  type="button"
                  aria-label="Decrement"
                  @click=${this._clickDown}></button>
    `;
  }
  static get styles() {
    return css`
    :host {
        display: flex;
        flex-direction: column;
        align-self: stretch;
        justify-content: space-evenly;
    }
    button {
        padding: 0;
        margin: 0;
        max-height: 0.9rem;
        box-sizing: content-box;
    }
    button::before {
        display: block;
    }
    #up::before {
        content: "\\25B2";
    }
    #down::before {
        content: "\\25BC";
    }`;
  }
  connectedCallback() {
    super.connectedCallback();
  }
  _clickUp() {
    this.dispatchEvent(new CustomEvent("click-up"));
  }
  _clickDown() {
    this.dispatchEvent(new CustomEvent("click-down"));
  }
}

defineCustomElement(FcDatePickerButtons);
