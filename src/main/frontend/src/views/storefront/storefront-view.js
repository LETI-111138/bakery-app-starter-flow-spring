import { html, css, LitElement } from 'lit';
import '@vaadin/grid';
import '@vaadin/dialog';
import '../../components/search-bar.js';
import './order-card.js';
import { sharedStyles } from '../../../styles/shared-styles.js';

/**
 * `StorefrontView` é um componente LitElement que exibe a visualização principal de pedidos, incluindo uma barra de pesquisa,
 * uma grade de pedidos e um diálogo para interações adicionais. Este componente é ideal para uma página de vitrine
 * de pedidos em um sistema de pedidos de alimentos ou serviços.
 * 
 * Ele utiliza o **`vaadin-grid`** para exibir uma lista de pedidos e um **`vaadin-dialog`** para mostrar detalhes ou ações
 * adicionais relacionados aos pedidos.
 * 
 * @slot - Não há slots definidos diretamente no componente, mas ele pode ser estendido com conteúdo dinâmico.
 * 
 * @example
 * <storefront-view>
 *   <!-- Conteúdo personalizado aqui -->
 * </storefront-view>
 * 
 * @css
 * :host {
 *   display: flex;
 *   flex-direction: column;
 *   height: 100%;
 * }
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
class StorefrontView extends LitElement {
  static get styles() {
    return [
      sharedStyles,
      css`
        :host {
          display: flex;
          flex-direction: column;
          height: 100%;
        }
      `,
    ];
  }

  render() {
    return html`
      <search-bar id="search" show-checkbox=""></search-bar>

      <vaadin-grid id="grid" theme="orders no-row-borders"></vaadin-grid>

      <vaadin-dialog id="dialog" theme="orders"></vaadin-dialog>
    `;
  }

  static get is() {
    return 'storefront-view';
  }

  /**
   * Método chamado quando o componente é inicializado. Aqui, ele mede o desempenho de carregamento da página.
   * Ele também adiciona um ouvinte de evento para o **`vaadin-grid`** para marcar quando a página foi carregada.
   */
  ready() {
    super.ready();

    // This code is needed to measure the page load performance and can be safely removed
    // if there is no need for that.
    const grid = this.$.grid;
    const listener = () => {
      if (!grid.loading && window.performance.mark) {
        window.performance.mark('bakery-page-loaded');
        grid.removeEventListener('loading-changed', listener);
      }
    };
    grid.addEventListener('loading-changed', listener);
  }
}

customElements.define(StorefrontView.is, StorefrontView);
