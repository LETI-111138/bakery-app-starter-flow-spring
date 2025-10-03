import { html, css, LitElement } from 'lit';

/**
 * `ButtonsBarElement` é um componente de barra de botões, construído com a biblioteca Lit.
 * Este componente cria uma barra que pode conter botões em várias seções (esquerda, direita e informações).
 * Ele oferece um layout flexível e responsivo, com a capacidade de ocultar a sombra da caixa com o atributo `no-scroll`.
 * 
 * @slot left - Slot para adicionar conteúdo no lado esquerdo da barra (geralmente botões ou outros elementos).
 * @slot info - Slot para adicionar conteúdo de informações que será exibido à direita da barra (com flexibilidade para responsividade).
 * @slot right - Slot para adicionar conteúdo no lado direito da barra (geralmente botões ou outros elementos).
 *
 * @csspart info - O estilo que é aplicado ao conteúdo do slot `info`.
 *
 * @example
 * <buttons-bar>
 *   <vaadin-button slot="left">Left Button</vaadin-button>
 *   <div slot="info">Information</div>
 *   <vaadin-button slot="right">Right Button</vaadin-button>
 * </buttons-bar>
 * 
 * @css
 * :host {
 *   flex: none;
 *   display: flex;
 *   flex-wrap: wrap;
 *   transition: box-shadow 0.2s;
 *   justify-content: space-between;
 *   padding-top: var(--lumo-space-s);
 *   align-items: baseline;
 *   box-shadow: 0 -3px 3px -3px var(--lumo-shade-20pct);
 * }
 * 
 * :host([no-scroll]) {
 *   box-shadow: none;
 * }
 * 
 * :host ::slotted([slot='info']),
 * .info {
 *   text-align: right;
 *   flex: 1;
 * }
 * 
 * @media (max-width: 600px) {
 *   :host ::slotted([slot='info']) {
 *     order: -1;
 *     min-width: 100%;
 *     flex-basis: 100%;
 *   }
 * }
 * 
 * @author Guilherme Teixeira
 * @version 1.0
 */
class ButtonsBarElement extends LitElement {
  static get styles() {
    return css`
      :host {
        flex: none;
        display: flex;
        flex-wrap: wrap;
        transition: box-shadow 0.2s;
        justify-content: space-between;
        padding-top: var(--lumo-space-s);
        align-items: baseline;
        box-shadow: 0 -3px 3px -3px var(--lumo-shade-20pct);
      }

      :host([no-scroll]) {
        box-shadow: none;
      }

      :host ::slotted([slot='info']),
      .info {
        text-align: right;
        flex: 1;
      }

      ::slotted(vaadin-button) {
        margin: var(--lumo-space-xs);
      }

      @media (max-width: 600px) {
        :host ::slotted([slot='info']) {
          order: -1;
          min-width: 100%;
          flex-basis: 100%;
        }
      }
    `;
  }

  render() {
    return html`
      <slot name="left"></slot>
      <slot name="info"><div class="info"></div></slot>
      <slot name="right"></slot>
    `;
  }

  static get is() {
    return 'buttons-bar';
  }
}

customElements.define(ButtonsBarElement.is, ButtonsBarElement);
