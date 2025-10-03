package com.vaadin.starter.bakery.backend.service;

import java.util.Optional;

import com.vaadin.starter.bakery.backend.data.entity.AbstractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A interface {@link FilterableCrudService} estende {@link CrudService} e adiciona métodos para
 * realizar operações de CRUD com suporte a filtros de pesquisa.
 * <p>
 * Esta interface define métodos que permitem buscar e contar entidades que correspondem a um filtro
 * opcional de string, com suporte para paginação. O filtro pode ser aplicado a qualquer campo da entidade
 * para realizar uma busca mais flexível e dinâmica.
 * </p>
 * 
 * @param <T> O tipo da entidade que estende {@link AbstractEntity}.
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface FilterableCrudService<T extends AbstractEntity> extends CrudService<T> {

    /**
     * Retorna uma página de entidades que correspondem ao filtro fornecido, com base nas informações de paginação.
     * Se o filtro não for fornecido, a busca retornará todos os itens sem restrição.
     * 
     * @param filter O filtro opcional para a pesquisa. Caso não seja fornecido, todos os itens são retornados.
     * @param pageable As informações de paginação.
     * @return Uma página de entidades que correspondem ao filtro fornecido.
     */
    Page<T> findAnyMatching(Optional<String> filter, Pageable pageable);

    /**
     * Retorna a contagem de entidades que correspondem ao filtro fornecido.
     * Se o filtro não for fornecido, a contagem retornará o número total de itens.
     * 
     * @param filter O filtro opcional para a pesquisa. Caso não seja fornecido, a contagem retornará o total de itens.
     * @return O número de entidades que correspondem ao filtro fornecido.
     */
    long countAnyMatching(Optional<String> filter);
}
