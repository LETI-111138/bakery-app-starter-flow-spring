package com.vaadin.starter.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * A classe {@link User} representa um usuário no sistema.
 * Ela contém informações sobre o e-mail, senha, nome, papel (role), e o status de bloqueio.
 * <p>
 * O e-mail do usuário deve ser único e será armazenado em minúsculas. O campo de senha armazena
 * o hash da senha do usuário. O campo de papel define o papel do usuário, como "admin" ou "usuário".
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Entity(name = "UserInfo")
public class User extends AbstractEntity {

    /**
     * O e-mail do usuário. Este campo é obrigatório, deve ser único e validado como um endereço de e-mail.
     * O e-mail é armazenado em letras minúsculas.
     */
    @NotEmpty
    @Email
    @Size(max = 255)
    @Column(unique = true)
    private String email;

    /**
     * O hash da senha do usuário. Este campo é obrigatório e deve ter no mínimo 4 caracteres.
     */
    @NotNull
    @Size(min = 4, max = 255)
    private String passwordHash;

    /**
     * O primeiro nome do usuário. Este campo é obrigatório.
     */
    @NotBlank
    @Size(max = 255)
    private String firstName;

    /**
     * O sobrenome do usuário. Este campo é obrigatório.
     */
    @NotBlank
    @Size(max = 255)
    private String lastName;

    /**
     * O papel (role) do usuário no sistema, como "admin" ou "usuário". Este campo é obrigatório.
     */
    @NotBlank
    @Size(max = 255)
    private String role;

    /**
     * O status de bloqueio do usuário. Indica se o usuário está bloqueado.
     * O valor padrão é {@code false}.
     */
    private boolean locked = false;

    /**
     * Método chamado antes de persistir ou atualizar o usuário.
     * Este método garante que o e-mail seja armazenado em minúsculas.
     */
    @PrePersist
    @PreUpdate
    private void prepareData() {
        this.email = email == null ? null : email.toLowerCase();
    }

    /**
     * Construtor vazio necessário para o Spring Data JPA.
     */
    public User() {
        // An empty constructor is needed for all beans
    }

    /**
     * Retorna o hash da senha do usuário.
     * 
     * @return O hash da senha.
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Define o hash da senha do usuário.
     * 
     * @param passwordHash O hash da senha a ser definido.
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Retorna o primeiro nome do usuário.
     * 
     * @return O primeiro nome do usuário.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Define o primeiro nome do usuário.
     * 
     * @param firstName O primeiro nome a ser definido.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retorna o sobrenome do usuário.
     * 
     * @return O sobrenome do usuário.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Define o sobrenome do usuário.
     * 
     * @param lastName O sobrenome a ser definido.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retorna o papel (role) do usuário no sistema.
     * 
     * @return O papel do usuário.
     */
    public String getRole() {
        return role;
    }

    /**
     * Define o papel (role) do usuário no sistema.
     * 
     * @param role O papel a ser definido.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retorna o e-mail do usuário.
     * 
     * @return O e-mail do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o e-mail do usuário. O e-mail será armazenado em letras minúsculas.
     * 
     * @param email O e-mail a ser definido.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna o status de bloqueio do usuário.
     * 
     * @return {@code true} se o usuário estiver bloqueado, {@code false} caso contrário.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Define o status de bloqueio do usuário.
     * 
     * @param locked O status de bloqueio a ser definido.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Compara este usuário com outro objeto para verificar se são iguais.
     * A comparação é feita com base no e-mail, nome, sobrenome, papel e status de bloqueio.
     * 
     * @param o O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        User that = (User) o;
        return locked == that.locked &&
                Objects.equals(email, that.email) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(role, that.role);
    }

    /**
     * Retorna o código hash do usuário, gerado com base no e-mail, nome, sobrenome, papel e status de bloqueio.
     * 
     * @return O código hash do usuário.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, firstName, lastName, role, locked);
    }
}
