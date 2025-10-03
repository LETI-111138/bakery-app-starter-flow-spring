/**
 * A mixin {@link ScrollShadowMixin} adiciona um comportamento para detectar se o conteúdo de um elemento com o ID `#main`
 * está sendo rolado e se há conteúdo oculto na parte inferior. Caso haja conteúdo oculto, ele adiciona o atributo `no-scroll`
 * ao elemento, indicando que o conteúdo não pode ser rolado até o final.
 * 
 * Este comportamento é útil quando você deseja adicionar um efeito de sombra de rolagem para indicar que o conteúdo está
 * sendo rolado e há mais conteúdo abaixo que não é visível.
 * 
 * @example
 * <custom-element no-scroll>
 *   <div id="main">
 *     <!-- Conteúdo aqui -->
 *   </div>
 * </custom-element>
 * 
 * @param {Constructor} subclass A classe que será estendida por esse mixin.
 * 
 * @returns {Constructor} A classe estendida com a funcionalidade de sombra de rolagem.
 * 
 * @throws {Error} Lança erro se o elemento com `#main` não for encontrado.
 * 
 * @author Guilherme Teixeira
 * @version 1.0
 */
export const ScrollShadowMixin = (subclass) =>
  class extends subclass {
    static get properties() {
      return {
        noScroll: {
          type: Boolean,
          reflect: true,
          attribute: 'no-scroll',
        },
        _main: {
          attribute: false,
        },
      };
    }

    /**
     * Método chamado após a primeira atualização do componente.
     * Aqui, o mixin escuta o evento de rolagem e executa a lógica para adicionar a sombra de rolagem
     * se o conteúdo não for rolável até o final.
     */
    firstUpdated() {
      super.firstUpdated();

      this._main = this.shadowRoot.querySelector('#main');

      if (this._main) {
        this._main.addEventListener('scroll', () => this._contentScroll());
        this._contentScroll();
      }
    }

    /**
     * Lógica para detectar se o conteúdo do elemento com `#main` é rolável até o final.
     * Caso não seja, o atributo `no-scroll` é adicionado ao elemento, indicando que o conteúdo
     * não pode ser rolado até o fim.
     */
    _contentScroll() {
      if (this._main) {
        this.noScroll =
          this._main.scrollHeight - this._main.scrollTop == this._main.clientHeight;
      }
    }
  };
