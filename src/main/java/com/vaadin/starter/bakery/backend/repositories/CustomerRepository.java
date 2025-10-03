package com.vaadin.starter.bakery.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaadin.starter.bakery.backend.data.entity.Customer;

/**
 * O repositório {@link CustomerRepository} é uma interface que estende {@link JpaRepository},
 * permitindo o acesso e a manipulação dos dados da entidade {@link Customer} no banco de dados.
 * <p>
 * A interface herda métodos prontos de CRUD (Criar, Ler, Atualizar e Excluir) do {@link JpaRepository},
 * além de permitir a criação de consultas personalizadas para as entidades {@link Customer}.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // A interface herda todos os métodos CRUD do JpaRepository,
    // então não há necessidade de definir novos métodos aqui, a menos que consultas personalizadas sejam necessárias.
}
