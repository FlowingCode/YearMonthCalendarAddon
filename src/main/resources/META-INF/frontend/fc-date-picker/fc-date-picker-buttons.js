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
       <div id="up">
          <button type="button"
                  aria-label="Increment"
                  @click=${this._clickUp}></button>
       </div>
       <div id="down">
          <button type="button"
                  aria-label="Decrement"
                  @click=${this._clickDown}></button>
       </div>
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
    div {
        min-height: 50%;
        display: flex;
        padding-left: calc(0.575em + var(--lumo-border-radius-m) / 4 - 1px);
    }
    div#up {
        align-items: flex-end;
    }
    div#down {
        align-items: flex-start;
    }
    button {
        padding: 0;
        max-height: 0.9rem;
        line-height: 100%;
        border: none;
        background-color: transparent;
        color: var(--lumo-contrast-60pct);
    }
    button::before {
        display: block;
    }
    #up button::before {
        content: "\\25B2";
    }
    #down button::before {
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
