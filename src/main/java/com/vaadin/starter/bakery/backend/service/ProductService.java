package com.vaadin.starter.bakery.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.vaadin.starter.bakery.backend.data.entity.Product;
import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.ProductRepository;

/**
 * O serviço {@link ProductService} oferece a lógica de negócios para gerenciar os produtos no sistema.
 * Ele fornece operações de CRUD para criar, ler, atualizar e excluir produtos, além de permitir a busca de produtos
 * com base em um filtro de nome.
 * <p>
 * O serviço interage com o repositório de produtos {@link ProductRepository} para realizar as operações de persistência
 * no banco de dados. Além disso, ele lida com exceções de integridade de dados, como quando um nome de produto já existe.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Service
public class ProductService implements FilterableCrudService<Product> {

    private final ProductRepository productRepository;

    /**
     * Construtor que injeta a dependência do repositório de produtos.
     * 
     * @param productRepository O repositório de produtos.
     */
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retorna uma página de produtos que correspondem ao filtro fornecido. O filtro é aplicado ao nome do produto,
     * ignorando maiúsculas e minúsculas. Se o filtro não for fornecido, retorna todos os produtos.
     * 
     * @param filter O filtro opcional para o nome do produto.
     * @param pageable As informações de paginação.
     * @return Uma página de produtos que correspondem ao filtro.
     */
    @Override
    public Page<Product> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return productRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    /**
     * Conta o número de produtos que correspondem ao filtro fornecido. Se o filtro não for fornecido, retorna o número total.
     * O filtro é aplicado ao nome do produto, ignorando maiúsculas e minúsculas.
     * 
     * @param filter O filtro opcional para o nome do produto.
     * @return O número de produtos que correspondem ao filtro.
     */
    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return productRepository.countByNameLikeIgnoreCase(repositoryFilter);
        } else {
            return count();
        }
    }

    /**
     * Retorna todos os produtos, paginados.
     * 
     * @param pageable As informações de paginação.
     * @return Uma página de produtos.
     */
    public Page<Product> find(Pageable pageable) {
        return productRepository.findBy(pageable);
    }

    /**
     * Retorna o repositório de produtos para realizar operações CRUD.
     * 
     * @return O repositório de produtos.
     */
    @Override
    public JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    /**
     * Cria uma nova instância de {@link Product}.
     * 
     * @param currentUser O usuário que está criando o novo produto.
     * @return O novo produto.
     */
    @Override
    public Product createNew(User currentUser) {
        return new Product();
    }

    /**
     * Salva um produto no banco de dados. Se o nome do produto já existir, uma exceção personalizada será lançada.
     * 
     * @param currentUser O usuário que está salvando o produto.
     * @param entity O produto a ser salvo.
     * @return O produto salvo.
     * @throws UserFriendlyDataException Caso o nome do produto já exista.
     */
    @Override
    public Product save(User currentUser, Product entity) {
        try {
            return FilterableCrudService.super.save(currentUser, entity);
        } catch (DataIntegrityViolationException e) {
            throw new UserFriendlyDataException(
                    "There is already a product with that name. Please select a unique name for the product.");
        }
    }
}
