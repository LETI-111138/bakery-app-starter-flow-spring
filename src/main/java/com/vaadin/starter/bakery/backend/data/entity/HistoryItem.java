package com.vaadin.starter.bakery.backend.data.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.vaadin.starter.bakery.backend.data.OrderState;

/**
 * A classe {@link HistoryItem} representa um item de histórico associado a um pedido,
 * armazenando informações sobre as mudanças no estado de um pedido e o usuário que
 * realizou a alteração.
 * <p>
 * Cada instância de {@link HistoryItem} contém o estado novo do pedido, uma mensagem
 * descritiva da alteração, a data e hora da alteração e o usuário responsável por ela.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Entity
public class HistoryItem extends AbstractEntity {

    /**
     * O novo estado do pedido associado a este item de histórico.
     * O estado representa a nova situação do pedido após a alteração.
     */
    private OrderState newState;

    /**
     * A mensagem descritiva que explica a alteração feita no pedido.
     * Este campo é obrigatório e não pode ser vazio.
     */
    @NotBlank
    @Size(max = 255)
    private String message;

    /**
     * O timestamp que representa a data e hora em que a alteração foi realizada.
     * Este campo é obrigatório e não pode ser nulo.
     */
    @NotNull
    private LocalDateTime timestamp;

    /**
     * O usuário responsável pela alteração. Este campo é obrigatório.
     * Ele é uma referência a uma entidade {@link User}.
     */
    @ManyToOne
    @NotNull
    private User createdBy;

    /**
     * Construtor vazio necessário para o Spring Data JPA.
     */
    HistoryItem() {
        // Empty constructor is needed by Spring Data / JPA
    }

    /**
     * Construtor para inicializar um item de histórico com o usuário que fez a alteração
     * e a mensagem associada à alteração.
     * O timestamp será automaticamente atribuído ao momento atual.
     * 
     * @param createdBy O usuário responsável pela alteração.
     * @param message A mensagem descrevendo a alteração.
     */
    public HistoryItem(User createdBy, String message) {
        this.createdBy = createdBy;
        this.message = message;
        timestamp = LocalDateTime.now();
    }

    /**
     * Retorna o novo estado do pedido após a alteração.
     * 
     * @return O novo estado do pedido.
     */
    public OrderState getNewState() {
        return newState;
    }

    /**
     * Define o novo estado do pedido.
     * 
     * @param newState O novo estado do pedido.
     */
    public void setNewState(OrderState newState) {
        this.newState = newState;
    }

    /**
     * Retorna a mensagem descritiva associada à alteração no pedido.
     * 
     * @return A mensagem associada à alteração.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Define a mensagem descritiva associada à alteração no pedido.
     * 
     * @param message A mensagem associada à alteração.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retorna a data e hora da alteração no pedido.
     * 
     * @return O timestamp da alteração.
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Define a data e hora da alteração no pedido.
     * 
     * @param timestamp A data e hora da alteração.
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Retorna o usuário responsável pela alteração.
     * 
     * @return O usuário responsável pela alteração.
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * Define o usuário responsável pela alteração.
     * 
     * @param createdBy O usuário responsável pela alteração.
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
