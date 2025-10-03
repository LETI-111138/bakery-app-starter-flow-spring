package com.vaadin.starter.bakery.backend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vaadin.starter.bakery.backend.data.entity.User;

/**
 * O repositório {@link UserRepository} é uma interface que estende {@link JpaRepository},
 * permitindo o acesso e a manipulação dos dados da entidade {@link User} no banco de dados.
 * <p>
 * A interface herda métodos prontos de CRUD (Criar, Ler, Atualizar e Excluir) do {@link JpaRepository},
 * além de fornecer métodos personalizados para buscar usuários com base em filtros como e-mail, nome ou papel.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Retorna o usuário com o e-mail fornecido, ignorando maiúsculas e minúsculas.
     * 
     * @param email O e-mail do usuário a ser buscado.
     * @return O usuário com o e-mail fornecido, ou {@code null} se não encontrado.
     */
    User findByEmailIgnoreCase(String email);

    /**
     * Retorna uma página de usuários com base nas informações de paginação fornecidas.
     * 
     * @param pageable As informações de paginação.
     * @return Uma página de usuários.
     */
    Page<User> findBy(Pageable pageable);

    /**
     * Retorna uma página de usuários com base em um filtro que pode ser aplicado a diversos campos,
     * incluindo e-mail, nome e papel. A pesquisa é realizada ignorando maiúsculas e minúsculas.
     * 
     * @param emailLike O filtro para o e-mail dos usuários.
     * @param firstNameLike O filtro para o primeiro nome dos usuários.
     * @param lastNameLike O filtro para o sobrenome dos usuários.
     * @param roleLike O filtro para o papel (role) dos usuários.
     * @param pageable As informações de paginação.
     * @return Uma página de usuários que correspondem aos filtros fornecidos.
     */
    Page<User> findByEmailLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCaseOrRoleLikeIgnoreCase(
            String emailLike, String firstNameLike, String lastNameLike, String roleLike, Pageable pageable);

    /**
     * Conta o número de usuários que correspondem aos filtros fornecidos para e-mail, nome ou papel,
     * ignorando maiúsculas e minúsculas.
     * 
     * @param emailLike O filtro para o e-mail dos usuários.
     * @param firstNameLike O filtro para o primeiro nome dos usuários.
     * @param lastNameLike O filtro para o sobrenome dos usuários.
     * @param roleLike O filtro para o papel (role) dos usuários.
     * @return O número de usuários que correspondem aos filtros fornecidos.
     */
    long countByEmailLikeIgnoreCaseOrFirstNameLikeIgnoreCaseOrLastNameLikeIgnoreCaseOrRoleLikeIgnoreCase(
            String emailLike, String firstNameLike, String lastNameLike, String roleLike);
}
