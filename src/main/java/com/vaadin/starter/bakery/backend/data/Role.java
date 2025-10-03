package com.vaadin.starter.bakery.backend.data;

/**
 * A classe {@link Role} contém constantes que representam os diferentes papéis (roles) no sistema.
 * Os papéis são utilizados para controlar o acesso a diferentes áreas e funcionalidades do sistema.
 * <p>
 * Os papéis definidos são:
 * <ul>
 *   <li>{@link #BARISTA}: Papel de barista, com permissões específicas para o atendimento de pedidos.</li>
 *   <li>{@link #BAKER}: Papel de padeiro, com permissões para gerenciar itens e preparar pedidos.</li>
 *   <li>{@link #ADMIN}: Papel de administrador, que tem acesso completo ao sistema.</li>
 * </ul>
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public class Role {

    /**
     * O papel de barista, com permissões específicas para o atendimento de pedidos.
     */
    public static final String BARISTA = "barista";

    /**
     * O papel de padeiro, com permissões para gerenciar itens e preparar pedidos.
     */
    public static final String BAKER = "baker";

    /**
     * O papel de administrador, que tem acesso completo ao sistema e todas as permissões.
     * Este papel implicitamente permite acesso a todas as áreas do sistema.
     */
    public static final String ADMIN = "admin";

    /**
     * Construtor privado para evitar a instância da classe {@link Role}.
     * A classe é apenas para acesso estático aos papéis definidos.
     */
    private Role() {
        // Static methods and fields only
    }

    /**
     * Retorna uma lista de todos os papéis definidos no sistema.
     * 
     * @return Um array contendo todos os papéis definidos: BARISTA, BAKER e ADMIN.
     */
    public static String[] getAllRoles() {
        return new String[] { BARISTA, BAKER, ADMIN };
    }
}
