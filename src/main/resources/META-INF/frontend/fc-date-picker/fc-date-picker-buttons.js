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
