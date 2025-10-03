package com.vaadin.starter.bakery.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaadin.starter.bakery.backend.data.entity.HistoryItem;

/**
 * O repositório {@link HistoryItemRepository} é uma interface que estende {@link JpaRepository},
 * permitindo o acesso e a manipulação dos dados da entidade {@link HistoryItem} no banco de dados.
 * <p>
 * A interface herda métodos prontos de CRUD (Criar, Ler, Atualizar e Excluir) do {@link JpaRepository},
 * permitindo que você realize operações básicas de persistência sem necessidade de implementação adicional.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface HistoryItemRepository extends JpaRepository<HistoryItem, Long> {
    // A interface herda todos os métodos CRUD do JpaRepository,
    // então não há necessidade de definir novos métodos aqui, a menos que consultas personalizadas sejam necessárias.
}
