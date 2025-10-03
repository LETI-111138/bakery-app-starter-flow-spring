package com.vaadin.starter.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * A classe {@link Customer} representa um cliente no sistema.
 * Ela estende {@link AbstractEntity}, herdando os atributos {@code id} e {@code version}.
 * <p>
 * Esta entidade é mapeada para a tabela {@code customer} no banco de dados e contém informações
 * relacionadas a um cliente, como o nome completo, número de telefone e detalhes adicionais.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Entity
public class Customer extends AbstractEntity {

    /**
     * O nome completo do cliente.
     * <p>
     * Este campo é obrigatório e deve ter no máximo 255 caracteres.
     * </p>
     */
    @NotBlank
    @Size(max = 255)
    private String fullName;

    /**
     * O número de telefone do cliente.
     * <p>
     * Este campo é obrigatório e deve ser validado conforme a expressão regular
     * que permite um prefixo internacional opcional e números de telefone com
     * um número variável de dígitos, separados por espaços ou traços.
     * </p>
     * A mensagem de erro para números de telefone inválidos é configurada por meio do
     * atributo {@code message} da anotação {@link Pattern}.
     * </p>
     */
    @NotBlank
    @Size(max = 20, message = "{bakery.phone.number.invalid}")
    @Pattern(regexp = "^(\\+\\d+)?([ -]?\\d+){4,14}$", message = "{bakery.phone.number.invalid}")
    private String phoneNumber;
    
    /**
     * Detalhes adicionais sobre o cliente.
     * Este campo não é obrigatório e tem um tamanho máximo de 255 caracteres.
     */
    @Size(max = 255)
    private String details;

    /**
     * Retorna o nome completo do cliente.
     * 
     * @return O nome completo do cliente.
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Define o nome completo do cliente.
     * 
     * @param fullName O nome completo do cliente.
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Retorna o número de telefone do cliente.
     * 
     * @return O número de telefone do cliente.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Define o número de telefone do cliente.
     * 
     * @param phoneNumber O número de telefone do cliente.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Retorna os detalhes adicionais sobre o cliente.
     * 
     * @return Os detalhes adicionais do cliente.
     */
    public String getDetails() {
        return details;
    }

    /**
     * Define os detalhes adicionais sobre o cliente.
     * 
     * @param details Os detalhes adicionais do cliente.
     */
    public void setDetails(String details) {
        this.details = details;
    }
}
