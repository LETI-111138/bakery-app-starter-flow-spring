package com.vaadin.starter.bakery.backend.data;

import java.util.Locale;

import com.vaadin.flow.shared.util.SharedUtil;

/**
 * A enumeração {@link OrderState} representa os diferentes estados que um pedido pode ter no sistema.
 * Cada valor da enumeração corresponde a um estado específico do pedido, como "Novo", "Confirmado",
 * "Pronto", "Entregue", "Problema" ou "Cancelado".
 * <p>
 * Esta enumeração é usada para controlar o ciclo de vida do pedido, permitindo acompanhar o estado
 * de um pedido desde sua criação até a entrega ou o cancelamento.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public enum OrderState {
    NEW, CONFIRMED, READY, DELIVERED, PROBLEM, CANCELLED;

    /**
     * Retorna uma versão amigável do identificador da enumeração, formatada para ser mais legível para o usuário.
     * A versão retornada estará em formato "Capitalized", ou seja, a primeira letra em maiúscula e as demais em minúscula.
     * 
     * Exemplo: o valor {@code "NEW"} será convertido para {@code "New"}.
     * 
     * @return A versão amigável do identificador da enumeração, com a primeira letra maiúscula.
     */
    public String getDisplayName() {
        return SharedUtil.capitalize(name().toLowerCase(Locale.ENGLISH));
    }
}
