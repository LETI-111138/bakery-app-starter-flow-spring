package com.vaadin.starter.bakery.backend.service;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.starter.bakery.backend.data.entity.AbstractEntity;
import com.vaadin.starter.bakery.backend.data.entity.User;

/**
 * A interface {@link CrudService} define operações básicas de CRUD (Criar, Ler, Atualizar e Excluir) 
 * para qualquer entidade que estenda {@link AbstractEntity}.
 * <p>
 * Ela oferece métodos para salvar, excluir e carregar entidades, além de métodos para contar a quantidade
 * de entidades e criar novas instâncias. O serviço também pode ser utilizado para realizar operações
 * com a entidade associada ao repositório correspondente.
 * </p>
 * 
 * @param <T> O tipo da entidade que estende {@link AbstractEntity}.
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface CrudService<T extends AbstractEntity> {

    /**
     * Retorna o repositório JPA associado à entidade.
     * 
     * @return O repositório JPA para a entidade {@code T}.
     */
    JpaRepository<T, Long> getRepository();

    /**
     * Salva a entidade fornecida e a persiste no banco de dados.
     * O método realiza um **flush** para garantir que a entidade seja salva imediatamente.
     * 
     * @param currentUser O usuário atual que realiza a operação de salvar.
     * @param entity A entidade a ser salva.
     * @return A entidade salva.
     */
    default T save(User currentUser, T entity) {
        return getRepository().saveAndFlush(entity);
    }

    /**
     * Exclui a entidade fornecida do banco de dados.
     * Se a entidade for nula, uma exceção {@link EntityNotFoundException} será lançada.
     * 
     * @param currentUser O usuário atual que realiza a operação de exclusão.
     * @param entity A entidade a ser excluída.
     */
    default void delete(User currentUser, T entity) {
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        getRepository().delete(entity);
    }

    /**
     * Exclui a entidade com o ID fornecido. Se a entidade não for encontrada, uma exceção
     * {@link EntityNotFoundException} será lançada.
     * 
     * @param currentUser O usuário atual que realiza a operação de exclusão.
     * @param id O ID da entidade a ser excluída.
     */
    default void delete(User currentUser, long id) {
        delete(currentUser, load(id));
    }

    /**
     * Retorna o número total de entidades do tipo {@code T} no banco de dados.
     * 
     * @return O número total de entidades {@code T}.
     */
    default long count() {
        return getRepository().count();
    }

    /**
     * Carrega a entidade com o ID fornecido. Se a entidade não for encontrada, uma exceção
     * {@link EntityNotFoundException} será lançada.
     * 
     * @param id O ID da entidade a ser carregada.
     * @return A entidade encontrada.
     */
    default T load(long id) {
        T entity = getRepository().findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    /**
     * Cria uma nova instância da entidade {@code T}.
     * Este método deve ser implementado pelas classes concretas para fornecer a lógica de criação da entidade.
     * 
     * @param currentUser O usuário atual que está criando a nova entidade.
     * @return A nova instância da entidade.
     */
    T createNew(User currentUser);
}
