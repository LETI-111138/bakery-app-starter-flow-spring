package com.vaadin.starter.bakery.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.starter.bakery.backend.data.entity.PickupLocation;

/**
 * O repositório {@link PickupLocationRepository} é uma interface que estende {@link JpaRepository},
 * permitindo o acesso e a manipulação dos dados da entidade {@link PickupLocation} no banco de dados.
 * <p>
 * A interface herda métodos prontos de CRUD (Criar, Ler, Atualizar e Excluir) do {@link JpaRepository},
 * permitindo que você realize operações básicas de persistência sem necessidade de implementação adicional.
 * Além disso, ela define métodos personalizados para buscar e contar locais de retirada com base em
 * filtros de nome, ignorando maiúsculas e minúsculas.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface PickupLocationRepository extends JpaRepository<PickupLocation, Long> {

    /**
     * Retorna uma página de locais de retirada com base no nome fornecido.
     * A pesquisa é realizada ignorando maiúsculas e minúsculas no nome.
     * 
     * @param nameFilter O filtro de nome a ser aplicado à pesquisa.
     * @param pageable As informações de paginação.
     * @return Uma página de locais de retirada cujo nome corresponde ao filtro fornecido.
     */
    Page<PickupLocation> findByNameLikeIgnoreCase(String nameFilter, Pageable pageable);

    /**
     * Conta o número de locais de retirada cujo nome corresponde ao filtro fornecido.
     * A pesquisa é realizada ignorando maiúsculas e minúsculas no nome.
     * 
     * @param nameFilter O filtro de nome a ser aplicado à contagem.
     * @return O número de locais de retirada cujo nome corresponde ao filtro fornecido.
     */
    int countByNameLikeIgnoreCase(String nameFilter);
}
