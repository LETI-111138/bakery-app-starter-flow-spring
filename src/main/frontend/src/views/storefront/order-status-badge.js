import { html, css, LitElement } from 'lit';
import '@vaadin/icon';
import '@vaadin/icons';

/**
 * `OrderStatusBadge` é um componente LitElement utilizado para exibir o status de um pedido em um formato de etiqueta.
 * Ele utiliza cores e ícones diferentes para representar o status do pedido, como "novo", "pronto", "problema" e "entregue".
 * A etiqueta é estilizada com um fundo colorido e o texto é transformado em letras minúsculas.
 * 
 * @slot - Não há slots definidos diretamente no componente, mas o conteúdo pode ser personalizado via a propriedade `status`.
 * 
 * @csspart wrapper - O estilo aplicado ao contêiner da etiqueta, incluindo cores e espaçamento.
 * 
 * @example
 * <order-status-badge status="new"></order-status-badge>
 * 
 * @css
 * :host([status='ready']) #wrapper {
 *   color: var(--lumo-success-color);
 *   background: var(--lumo-success-color-10pct);
 * }
 * 
 * :host([status='new']) #wrapper {
 *   color: var(--lumo-primary-color);
 *   background: var(--lumo-primary-color-10pct);
 * }
 * 
 * @author Guilherme Teixeira
 * @version 1.0
 */
class OrderStatusBadge extends LitElement {
  static get styles() {
    return css`
      #wrapper {
        display: inline-block;
        border-radius: var(--lumo-border-radius);
        background: var(--lumo-shade-10pct);
        color: var(--lumo-secondary-text-color);
        padding: 2px 10px;
        font-size: var(--lumo-font-size-xs);
        text-transform: capitalize;
      }

      :host([status='ready']) #wrapper {
        color: var(--lumo-success-color);
        background: var(--lumo-success-color-10pct);
      }

      :host([status='new']) #wrapper {
        color: var(--lumo-primary-color);
        background: var(--lumo-primary-color-10pct);
      }

      :host([status='problem']) #wrapper {
        color: var(--lumo-error-color);
        background: var(--lumo-error-color-10pct);
      }

      :host([status='delivered']) #wrapper {
        padding: 2px 8px;
      }

      :host([status='delivered']) #wrapper span,
      :host(:not([status='delivered'])) #wrapper vaadin-icon {
        display: none;
      }

      :host([small]) #wrapper {
        padding: 0 5px;
      }

      vaadin-icon {
        width: 12px;
      }

      :host([small]) vaadin-icon {
        width: 8px;
      }
    `;
  }

  render() {
    return html`
      <div id="wrapper">
        <span>${this.__toLowerCase(this.status)}</span>
        <vaadin-icon icon="vaadin:check"></vaadin-icon>
      </div>
    `;
  }

  static get is() {
    return 'order-status-badge';
  }

  static get properties() {
    return {
      status: {
        type: String,
        reflect: true,
        converter: {
          fromAttribute: (value) => value.toUpperCase(),
          toAttribute: (value) => value.toLowerCase()
        }
      }
    };
  }

  /**
   * Converte o status para minúsculas.
   * 
   * @param {string} status O status a ser convertido.
   * @returns {string} O status convertido para minúsculas.
   */
  __toLowerCase(status) {
    return status ? status.toLowerCase() : '';
  }
}

customElements.define(OrderStatusBadge.is, OrderStatusBadge);
