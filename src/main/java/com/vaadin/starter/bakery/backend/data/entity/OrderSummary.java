package com.vaadin.starter.bakery.backend.data.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.vaadin.starter.bakery.backend.data.OrderState;

/**
 * A interface {@link OrderSummary} define os métodos essenciais para recuperar as informações
 * resumidas de um pedido. Ela é implementada por entidades que fornecem dados de um pedido
 * de maneira resumida, sem a necessidade de carregar toda a estrutura de dados associada ao pedido.
 * <p>
 * As implementações dessa interface devem fornecer dados como o identificador do pedido, o estado,
 * o cliente, os itens, a data e hora de entrega, e o local de retirada, além do preço total do pedido.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface OrderSummary {

    /**
     * Retorna o identificador único do pedido.
     * 
     * @return O identificador único do pedido.
     */
    Long getId();

    /**
     * Retorna o estado atual do pedido.
     * O estado do pedido é representado pela enumeração {@link OrderState}.
     * 
     * @return O estado atual do pedido.
     */
    OrderState getState();

    /**
     * Retorna o cliente associado ao pedido.
     * 
     * @return O cliente do pedido.
     */
    Customer getCustomer();

    /**
     * Retorna a lista de itens do pedido.
     * 
     * @return A lista de itens do pedido, representada por {@link OrderItem}.
     */
    List<OrderItem> getItems();

    /**
     * Retorna a data de entrega do pedido.
     * 
     * @return A data de entrega do pedido.
     */
    LocalDate getDueDate();

    /**
     * Retorna a hora de entrega do pedido.
     * 
     * @return A hora de entrega do pedido.
     */
    LocalTime getDueTime();

    /**
     * Retorna o local de retirada do pedido.
     * 
     * @return O local de retirada do pedido.
     */
    PickupLocation getPickupLocation();

    /**
     * Retorna o preço total do pedido, somando o preço de todos os itens.
     * 
     * @return O preço total do pedido.
     */
    Integer getTotalPrice();
}
