package com.vaadin.starter.bakery.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.vaadin.starter.bakery.backend.data.entity.PickupLocation;
import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.PickupLocationRepository;

/**
 * O serviço {@link PickupLocationService} fornece operações de lógica de negócios para gerenciar os locais de retirada de pedidos no sistema.
 * Ele permite buscar, contar e criar locais de retirada, além de fornecer filtros para encontrar locais com base em um termo de pesquisa.
 * <p>
 * O serviço utiliza a interface {@link PickupLocationRepository} para interagir com o banco de dados e realizar as operações CRUD (Criar, Ler, Atualizar e Excluir).
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Service
public class PickupLocationService implements FilterableCrudService<PickupLocation> {

    private final PickupLocationRepository pickupLocationRepository;

    /**
     * Construtor que injeta a dependência do repositório de locais de retirada.
     * 
     * @param pickupLocationRepository O repositório de locais de retirada.
     */
    @Autowired
    public PickupLocationService(PickupLocationRepository pickupLocationRepository) {
        this.pickupLocationRepository = pickupLocationRepository;
    }

    /**
     * Retorna uma página de locais de retirada que correspondem ao filtro fornecido, se presente.
     * Se o filtro não for fornecido, retorna todos os locais de retirada.
     * A pesquisa é realizada ignorando maiúsculas e minúsculas no nome.
     * 
     * @param filter O filtro opcional para a pesquisa de locais de retirada.
     * @param pageable As informações de paginação.
     * @return Uma página de locais de retirada que correspondem ao filtro.
     */
    public Page<PickupLocation> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return pickupLocationRepository.findByNameLikeIgnoreCase(repositoryFilter, pageable);
        } else {
            return pickupLocationRepository.findAll(pageable);
        }
    }

    /**
     * Retorna a contagem de locais de retirada que correspondem ao filtro fornecido.
     * Se o filtro não for fornecido, retorna o número total de locais de retirada.
     * A pesquisa é realizada ignorando maiúsculas e minúsculas no nome.
     * 
     * @param filter O filtro opcional para a pesquisa de locais de retirada.
     * @return O número de locais de retirada que correspondem ao filtro.
     */
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return pickupLocationRepository.countByNameLikeIgnoreCase(repositoryFilter);
        } else {
            return pickupLocationRepository.count();
        }
    }

    /**
     * Retorna o local de retirada padrão, utilizando um filtro vazio e a primeira página de resultados.
     * 
     * @return O local de retirada padrão.
     */
    public PickupLocation getDefault() {
        return findAnyMatching(Optional.empty(), PageRequest.of(0, 1)).iterator().next();
    }

    /**
     * Retorna o repositório de locais de retirada para realizar operações CRUD.
     * 
     * @return O repositório de locais de retirada.
     */
    @Override
    public JpaRepository<PickupLocation, Long> getRepository() {
        return pickupLocationRepository;
    }

    /**
     * Cria uma nova instância de {@link PickupLocation}.
     * 
     * @param currentUser O usuário que está criando o novo local de retirada.
     * @return O novo local de retirada.
     */
    @Override
    public PickupLocation createNew(User currentUser) {
        return new PickupLocation();
    }
}
