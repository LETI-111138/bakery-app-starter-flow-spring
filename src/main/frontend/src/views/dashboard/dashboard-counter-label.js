import { html, css, LitElement } from 'lit';

/**
 * `DashboardCounterLabel` é um componente de contador de painel, utilizado para exibir um número (ou contagem) com um título e subtítulo.
 * Ele permite aplicar cores personalizadas ao número (contagem) e exibe um gráfico ou conteúdo adicional no topo através de um **slot**.
 * <p>
 * O componente pode ser usado para exibir métricas importantes, como vendas, entregas, ou qualquer outro dado numérico com um estilo visual
 * que indica sua importância (como verde, vermelho, azul ou cinza).
 * </p>
 * 
 * @slot - Slot para o conteúdo do gráfico ou outro conteúdo adicional que deve ser exibido no topo do contador.
 * 
 * @csspart count-digit - A parte do componente que exibe a contagem, com suporte para personalização de cores.
 * @csspart subtitle - A parte do componente que exibe o subtítulo.
 * 
 * @example
 * <dashboard-counter-label class="green">
 *   <div>Some chart or extra content</div>
 *   <span slot="count">123</span>
 *   <span slot="title">Total Sales</span>
 *   <span slot="subtitle">This month</span>
 * </dashboard-counter-label>
 * 
 * @css
 * :host(.green) .count-digit {
 *   color: #55bf3b;
 * }
 * :host(.red) .count-digit {
 *   color: #ff473a;
 * }
 * :host(.blue) .count-digit {
 *   color: #1877f3;
 * }
 * :host(.gray) .count-digit {
 *   color: rgba(45, 71, 105, 0.7);
 * }
 * 
 * @author Guilherme Teixeira
 * @version 1.0
 */
class DashboardCounterLabel extends LitElement {
  static get styles() {
    return css`
      :host {
        position: relative;
        text-align: center;
        height: calc(18vh - 64px);
        min-height: 180px;
        display: block;
      }

      :host(.green) .count-digit {
        color: #55bf3b;
      }

      :host(.red) .count-digit {
        color: #ff473a;
      }

      :host(.blue) .count-digit {
        color: #1877f3;
      }

      :host(.gray) .count-digit {
        color: rgba(45, 71, 105, 0.7);
      }

      .content {
        padding: 10px;
      }

      .count-wrapper {
        display: block;
        text-align: center;
        padding-top: 12px;
        margin-bottom: 18px;
      }

      .count-digit {
        font-size: 44px;
      }

      .subtitle {
        color: var(--lumo-secondary-text-color);
        font-size: 14px;
      }

      h4 {
        margin: 0;
      }

      .chart-wrapper {
        position: absolute;
        top: 0;
        left: 0;
        height: 120px;
        width: 100%;
      }
    `;
  }

  render() {
    return html`
      <div class="chart-wrapper">
        <slot></slot>
      </div>

      <div class="content">
        <div class="count-wrapper">
          <span id="count" class="count-digit"></span>
        </div>

        <h4 id="title"></h4>
        <div id="subtitle" class="subtitle"></div>
      </div>
    `;
  }

  static get is() {
    return 'dashboard-counter-label';
  }
}

customElements.define(DashboardCounterLabel.is, DashboardCounterLabel);
