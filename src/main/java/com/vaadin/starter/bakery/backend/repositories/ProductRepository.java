package com.vaadin.starter.bakery.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.starter.bakery.backend.data.entity.Product;

/**
 * Interface que estende o {@link JpaRepository} para realizar operações de CRUD no banco de dados
 * para a entidade {@link Product}.
 * <p>
 * O {@link JpaRepository} oferece métodos automáticos para salvar, excluir e buscar produtos no banco de dados,
 * enquanto os métodos personalizados aqui fornecem funcionalidades adicionais de consulta e contagem.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Retorna uma página de produtos com base no critério de paginação fornecido.
     * 
     * @param page O objeto {@link Pageable} que define a página a ser retornada.
     * @return Uma página de produtos.
     */
    Page<Product> findBy(Pageable page);

    /**
     * Retorna uma página de produtos cujo nome contenha a string fornecida, ignorando
     * o caso, com base no critério de paginação fornecido.
     * 
     * @param name O nome a ser buscado.
     * @param page O objeto {@link Pageable} que define a página a ser retornada.
     * @return Uma página de produtos que atendem ao critério de busca.
     */
    Page<Product> findByNameLikeIgnoreCase(String name, Pageable page);

    /**
     * Conta o número de produtos cujo nome contenha a string fornecida, ignorando
     * o caso.
     * 
     * @param name O nome a ser buscado.
     * @return O número de produtos que atendem ao critério de busca.
     */
    int countByNameLikeIgnoreCase(String name);
}
