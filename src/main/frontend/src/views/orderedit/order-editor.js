import { html, css, LitElement } from 'lit';
import '@vaadin/icons/vaadin-icons.js';
import '@vaadin/button';
import '@vaadin/combo-box';
import '@vaadin/date-picker';
import '@vaadin/form-layout';
import '@vaadin/form-layout/vaadin-form-item.js';
import '@vaadin/icon';
import '@vaadin/icons';
import '../../components/buttons-bar.js';
import { ScrollShadowMixin } from '../../components/utils-mixin.js';
import './order-item-editor.js';
import { sharedStyles } from '../../../styles/shared-styles.js';

/**
 * `OrderEditor` é um componente LitElement que permite editar os detalhes de um pedido, incluindo o status do pedido,
 * a data e hora de entrega, informações do cliente e os produtos solicitados.
 * Ele fornece um formulário responsivo com campos de entrada como caixas de seleção, campos de texto e campos de data.
 * Além disso, o componente exibe um total de preço do pedido e permite revisar o pedido antes de finalizá-lo.
 * <p>
 * O layout é flexível e se adapta ao tamanho da tela, com a capacidade de alternar entre exibição em uma única coluna
 * ou várias colunas dependendo do tamanho da tela.
 * </p>
 *
 * @slot - Slot para adicionar conteúdo personalizado, como itens de produtos ou outras informações.
 * 
 * @csspart form1responsiveSteps - Responsividade do primeiro formulário.
 * @csspart form2responsiveSteps - Responsividade do segundo formulário.
 * @csspart form3responsiveSteps - Responsividade do terceiro formulário.
 * 
 * @example
 * <order-editor>
 *   <vaadin-text-field label="Customer Name" value="John Doe"></vaadin-text-field>
 * </order-editor>
 * 
 * @css
 * .meta-row {
 *   display: flex;
 *   justify-content: space-between;
 *   padding-bottom: var(--lumo-space-s);
 * }
 * 
 * .dim {
 *   color: var(--lumo-secondary-text-color);
 *   text-align: right;
 *   white-space: nowrap;
 *   line-height: 2.5em;
 * }
 * 
 * .status {
 *   width: 10em;
 * }
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
class OrderEditor extends ScrollShadowMixin(LitElement) {
  static get styles() {
    return [
      sharedStyles,
      css`
        :host {
          display: flex;
          flex-direction: column;
          flex: auto;
        }

        .meta-row {
          display: flex;
          justify-content: space-between;
          padding-bottom: var(--lumo-space-s);
        }

        .dim {
          color: var(--lumo-secondary-text-color);
          text-align: right;
          white-space: nowrap;
          line-height: 2.5em;
        }

        .status {
          width: 10em;
        }
      `,
    ];
  }

  render() {
    return html`
      <div class="scrollable flex1" id="main">
        <h2 id="title">New order</h2>

        <div class="meta-row" id="metaContainer">
          <vaadin-combo-box
            class="status"
            id="status"
            status="${this.__toLowerCase(this.status)}"
          ></vaadin-combo-box>
          <span class="dim">Order #<span id="orderNumber"></span></span>
        </div>

        <vaadin-form-layout
          id="form1"
          .responsiveSteps="${this.form1responsiveSteps}"
        >
          <vaadin-form-layout
            id="form2"
            .responsiveSteps="${this.form2responsiveSteps}"
          >
            <vaadin-date-picker label="Due" id="dueDate"> </vaadin-date-picker>
            <vaadin-combo-box id="dueTime">
              <vaadin-icon slot="prefix" icon="vaadin:clock"></vaadin-icon>
            </vaadin-combo-box>
            <vaadin-combo-box id="pickupLocation" colspan="2">
              <vaadin-icon slot="prefix" icon="vaadin:at"></vaadin-icon>
            </vaadin-combo-box>
          </vaadin-form-layout>

          <vaadin-form-layout
            id="form3"
            colspan="3"
            .responsiveSteps="${this.form3responsiveSteps}"
          >
            <vaadin-text-field id="customerName" label="Customer" colspan="2">
              <vaadin-icon slot="prefix" icon="vaadin:user"></vaadin-icon>
            </vaadin-text-field>

            <vaadin-text-field id="customerNumber" label="Phone number">
              <vaadin-icon slot="prefix" icon="vaadin:phone"></vaadin-icon>
            </vaadin-text-field>

            <vaadin-text-field
              id="customerDetails"
              label="Additional Details"
              colspan="2"
            ></vaadin-text-field>

            <vaadin-form-item colspan="3">
              <label slot="label">Products</label>
            </vaadin-form-item>
            <div id="itemsContainer" colspan="3"></div>
          </vaadin-form-layout>
        </vaadin-form-layout>
      </div>

      <buttons-bar id="footer" no-scroll="${this.noScroll}">
        <vaadin-button slot="left" id="cancel">Cancel</vaadin-button>
        <div slot="info" class="total">Total ${this.totalPrice}</div>
        <vaadin-button slot="right" id="review" theme="primary">
          Review order
          <vaadin-icon icon="vaadin:arrow-right" slot="suffix"></vaadin-icon>
        </vaadin-button>
      </buttons-bar>
    `;
  }

  static get is() {
    return 'order-editor';
  }

  static get properties() {
    return {
      status: {
        type: String,
      },
      totalPrice: {
        type: String,
      },
      form1responsiveSteps: {
        type: Array,
      },
      form2responsiveSteps: {
        type: Array,
      },
      form3responsiveSteps: {
        type: Array,
      },
    };
  }

  constructor() {
    super();

    /** @type {{ minWidth: string | 0; columns: number; labelsPosition: "top" | "aside"; }[]} */
    this.form1responsiveSteps = [
      { columns: 1, labelsPosition: 'top' },
      { minWidth: '600px', columns: 4, labelsPosition: 'top' },
    ];
    /** @type {{ minWidth: string | 0; columns: number; labelsPosition: "top" | "aside"; }[]} */
    this.form2responsiveSteps = [
      { columns: 1, labelsPosition: 'top' },
      { minWidth: '360px', columns: 2, labelsPosition: 'top' },
    ];
    /** @type {{ minWidth: string | 0; columns: number; labelsPosition: "top" | "aside"; }[]} */
    this.form3responsiveSteps = [
      { columns: 1, labelsPosition: 'top' },
      { minWidth: '500px', columns: 3, labelsPosition: 'top' },
    ];
  }

  __toLowerCase(status) {
    return status ? status.toLowerCase() : '';
  }
}

customElements.define(OrderEditor.is, OrderEditor);
