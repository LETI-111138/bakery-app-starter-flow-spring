package com.vaadin.starter.bakery.backend.data.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.NamedEntityGraphs;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.BatchSize;

import com.vaadin.starter.bakery.backend.data.OrderState;

/**
 * A classe {@link Order} representa um pedido no sistema.
 * Ela contém informações sobre o cliente, os itens do pedido, o estado do pedido e o histórico de alterações.
 * <p>
 * A classe é mapeada para a tabela {@code order_info} no banco de dados. O pedido contém dados
 * como a data e hora de entrega, o local de retirada, os itens do pedido, o cliente e o histórico de mudanças.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Entity(name = "OrderInfo") // "Order" is a reserved word
@NamedEntityGraphs({@NamedEntityGraph(name = Order.ENTITY_GRAPTH_BRIEF, attributeNodes = {
		@NamedAttributeNode("customer"),
		@NamedAttributeNode("pickupLocation")
}), @NamedEntityGraph(name = Order.ENTITY_GRAPTH_FULL, attributeNodes = {
		@NamedAttributeNode("customer"),
		@NamedAttributeNode("pickupLocation"),
		@NamedAttributeNode("items"),
		@NamedAttributeNode("history")
})})
@Table(indexes = @Index(columnList = "dueDate"))
public class Order extends AbstractEntity implements OrderSummary {

    public static final String ENTITY_GRAPTH_BRIEF = "Order.brief";
    public static final String ENTITY_GRAPTH_FULL = "Order.full";

    /**
     * A data de entrega do pedido.
     * Este campo é obrigatório e não pode ser nulo.
     */
    @NotNull(message = "{bakery.due.date.required}")
    private LocalDate dueDate;

    /**
     * A hora de entrega do pedido.
     * Este campo é obrigatório e não pode ser nulo.
     */
    @NotNull(message = "{bakery.due.time.required}")
    private LocalTime dueTime;

    /**
     * O local de retirada do pedido.
     * Este campo é obrigatório e representa o local onde o cliente deve retirar o pedido.
     */
    @NotNull(message = "{bakery.pickup.location.required}")
    @ManyToOne
    private PickupLocation pickupLocation;

    /**
     * O cliente associado ao pedido.
     * Este campo é obrigatório e mapeia para a entidade {@link Customer}.
     */
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    /**
     * A lista de itens do pedido.
     * Este campo é obrigatório e representa os produtos ou serviços solicitados pelo cliente.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderColumn
    @JoinColumn
    @BatchSize(size = 1000)
    @NotEmpty
    @Valid
    private List<OrderItem> items;

    /**
     * O estado atual do pedido.
     * Este campo é obrigatório e representa o estado do pedido (ex: "Novo", "Em preparo", "Concluído").
     */
    @NotNull(message = "{bakery.status.required}")
    private OrderState state;

    /**
     * O histórico de alterações do pedido.
     * Este campo é opcional e mantém um registro das mudanças no estado do pedido.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderColumn
    @JoinColumn
    private List<HistoryItem> history;

    /**
     * Construtor da classe {@link Order}. Inicializa o pedido com o estado "Novo" e define o cliente e o histórico.
     * 
     * @param createdBy O usuário responsável pela criação do pedido.
     */
    public Order(User createdBy) {
        this.state = OrderState.NEW;
        setCustomer(new Customer());
        addHistoryItem(createdBy, "Order placed");
        this.items = new ArrayList<>();
    }

    /**
     * Construtor vazio necessário para o Spring Data JPA.
     */
    Order() {
        // Empty constructor is needed by Spring Data / JPA
    }

    /**
     * Adiciona um item ao histórico do pedido.
     * 
     * @param createdBy O usuário responsável pela alteração.
     * @param comment A descrição da alteração.
     */
    public void addHistoryItem(User createdBy, String comment) {
        HistoryItem item = new HistoryItem(createdBy, comment);
        item.setNewState(state);
        if (history == null) {
            history = new LinkedList<>();
        }
        history.add(item);
    }

    /**
     * Retorna a data de entrega do pedido.
     * 
     * @return A data de entrega do pedido.
     */
    @Override
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Define a data de entrega do pedido.
     * 
     * @param dueDate A data de entrega do pedido.
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Retorna a hora de entrega do pedido.
     * 
     * @return A hora de entrega do pedido.
     */
    @Override
    public LocalTime getDueTime() {
        return dueTime;
    }

    /**
     * Define a hora de entrega do pedido.
     * 
     * @param dueTime A hora de entrega do pedido.
     */
    public void setDueTime(LocalTime dueTime) {
        this.dueTime = dueTime;
    }

    /**
     * Retorna o local de retirada do pedido.
     * 
     * @return O local de retirada do pedido.
     */
    @Override
    public PickupLocation getPickupLocation() {
        return pickupLocation;
    }

    /**
     * Define o local de retirada do pedido.
     * 
     * @param pickupLocation O local de retirada do pedido.
     */
    public void setPickupLocation(PickupLocation pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    /**
     * Retorna o cliente associado ao pedido.
     * 
     * @return O cliente associado ao pedido.
     */
    @Override
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Define o cliente associado ao pedido.
     * 
     * @param customer O cliente associado ao pedido.
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Retorna a lista de itens do pedido.
     * 
     * @return A lista de itens do pedido.
     */
    @Override
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Define a lista de itens do pedido.
     * 
     * @param items A lista de itens do pedido.
     */
    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    /**
     * Retorna o histórico de alterações do pedido.
     * 
     * @return O histórico de alterações do pedido.
     */
    public List<HistoryItem> getHistory() {
        return history;
    }

    /**
     * Define o histórico de alterações do pedido.
     * 
     * @param history O histórico de alterações do pedido.
     */
    public void setHistory(List<HistoryItem> history) {
        this.history = history;
    }

    /**
     * Retorna o estado atual do pedido.
     * 
     * @return O estado atual do pedido.
     */
    @Override
    public OrderState getState() {
        return state;
    }

    /**
     * Altera o estado do pedido e adiciona um item ao histórico se o estado for alterado.
     * 
     * @param user O usuário que alterou o estado do pedido.
     * @param state O novo estado do pedido.
     */
    public void changeState(User user, OrderState state) {
        boolean createHistory = this.state != state && this.state != null && state != null;
        this.state = state;
        if (createHistory) {
            addHistoryItem(user, "Order " + state);
        }
    }

    /**
     * Retorna uma representação em string do pedido.
     * 
     * @return A representação em string do pedido.
     */
    @Override
    public String toString() {
        return "Order{" + "dueDate=" + dueDate + ", dueTime=" + dueTime + ", pickupLocation=" + pickupLocation
                + ", customer=" + customer + ", items=" + items + ", state=" + state + '}';
    }

    /**
     * Retorna o preço total do pedido, somando o preço de todos os itens.
     * 
     * @return O preço total do pedido.
     */
    @Override
    public Integer getTotalPrice() {
        return items.stream().map(i -> i.getTotalPrice()).reduce(0, Integer::sum);
    }
}
