package com.vaadin.starter.bakery.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.UserRepository;

/**
 * O serviço {@link UserService} fornece operações de lógica de negócios para gerenciar usuários no sistema.
 * Ele oferece métodos para criar, salvar, excluir e buscar usuários, além de verificar se um usuário está bloqueado
 * ou se o usuário está tentando excluir sua própria conta.
 * <p>
 * O serviço interage com o repositório de usuários {@link UserRepository} para realizar as operações de persistência
 * no banco de dados e garantir regras de negócio, como impedir a modificação ou exclusão de usuários bloqueados
 * e a exclusão de sua própria conta.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Service
public class UserService implements FilterableCrudService<User> {

    public static final String MODIFY_LOCKED_USER_NOT_PERMITTED = "User has been locked and cannot be modified or deleted";
    private static final String DELETING_SELF_NOT_PERMITTED = "You cannot delete your own account";
    
    private final UserRepository userRepository;

    /**
     * Construtor que injeta a dependência do repositório de usuários.
     * 
     * @param userRepository O repositório de usuários.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retorna uma página de usuários que correspondem ao filtro fornecido, aplicando a pesquisa em e-mail,
     * primeiro nome, sobrenome ou papel (role). A pesquisa é feita ignorando maiúsculas e minúsculas.
     * 
     * @param filter O filtro opcional para a pesquisa de usuários.
     * @param pageable As informações de paginação.
     * @return Uma página de usuários que correspondem ao filtro fornecido.
     */
    public Page<User> findAnyMatching(Optional<String> filter, Pageable pageable) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return getRepository()
                    .findByEmailLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCaseOrRoleLikeIgnoreCase(
                            repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter, pageable);
        } else {
            return find(pageable);
        }
    }

    /**
     * Conta o número de usuários que correspondem ao filtro fornecido. O filtro é aplicado em e-mail,
     * primeiro nome, sobrenome ou papel (role), ignorando maiúsculas e minúsculas.
     * 
     * @param filter O filtro opcional para a pesquisa de usuários.
     * @return O número de usuários que correspondem ao filtro fornecido.
     */
    @Override
    public long countAnyMatching(Optional<String> filter) {
        if (filter.isPresent()) {
            String repositoryFilter = "%" + filter.get() + "%";
            return userRepository.countByEmailLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCaseOrRoleLikeIgnoreCase(
                    repositoryFilter, repositoryFilter, repositoryFilter, repositoryFilter);
        } else {
            return count();
        }
    }

    /**
     * Retorna o repositório de usuários para realizar operações CRUD.
     * 
     * @return O repositório de usuários.
     */
    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    /**
     * Retorna uma página de usuários.
     * 
     * @param pageable As informações de paginação.
     * @return Uma página de usuários.
     */
    public Page<User> find(Pageable pageable) {
        return getRepository().findBy(pageable);
    }

    /**
     * Salva um usuário no banco de dados. Antes de salvar, verifica se o usuário não está bloqueado.
     * 
     * @param currentUser O usuário que está fazendo a operação de salvar.
     * @param entity O usuário a ser salvo.
     * @return O usuário salvo.
     * @throws UserFriendlyDataException Se o usuário estiver bloqueado.
     */
    @Override
    public User save(User currentUser, User entity) {
        throwIfUserLocked(entity);
        return getRepository().saveAndFlush(entity);
    }

    /**
     * Exclui um usuário do banco de dados. Antes de excluir, verifica se o usuário está tentando excluir sua própria conta
     * ou se o usuário está bloqueado.
     * 
     * @param currentUser O usuário que está fazendo a operação de exclusão.
     * @param userToDelete O usuário a ser excluído.
     * @throws UserFriendlyDataException Se o usuário tentar excluir sua própria conta ou se o usuário estiver bloqueado.
     */
    @Override
    @Transactional
    public void delete(User currentUser, User userToDelete) {
        throwIfDeletingSelf(currentUser, userToDelete);
        throwIfUserLocked(userToDelete);
        FilterableCrudService.super.delete(currentUser, userToDelete);
    }

    /**
     * Lança uma exceção se o usuário tentar excluir sua própria conta.
     * 
     * @param currentUser O usuário atual.
     * @param user O usuário a ser excluído.
     * @throws UserFriendlyDataException Se o usuário tentar excluir sua própria conta.
     */
    private void throwIfDeletingSelf(User currentUser, User user) {
        if (currentUser.equals(user)) {
            throw new UserFriendlyDataException(DELETING_SELF_NOT_PERMITTED);
        }
    }

    /**
     * Lança uma exceção se o usuário estiver bloqueado e não puder ser modificado ou excluído.
     * 
     * @param entity O usuário a ser verificado.
     * @throws UserFriendlyDataException Se o usuário estiver bloqueado.
     */
    private void throwIfUserLocked(User entity) {
        if (entity != null && entity.isLocked()) {
            throw new UserFriendlyDataException(MODIFY_LOCKED_USER_NOT_PERMITTED);
        }
    }

    /**
     * Cria uma nova instância de {@link User}.
     * 
     * @param currentUser O usuário que está criando o novo usuário.
     * @return O novo usuário.
     */
    @Override
    public User createNew(User currentUser) {
        return new User();
    }

}
