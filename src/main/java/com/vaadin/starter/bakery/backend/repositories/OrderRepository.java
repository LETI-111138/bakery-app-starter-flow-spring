package com.vaadin.starter.bakery.backend.repositories;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vaadin.starter.bakery.backend.data.OrderState;
import com.vaadin.starter.bakery.backend.data.entity.Order;
import com.vaadin.starter.bakery.backend.data.entity.OrderSummary;

/**
 * A interface {@link OrderRepository} é responsável por fornecer métodos para a manipulação de dados
 * relacionados aos pedidos no sistema.
 * Ela estende {@link JpaRepository}, o que fornece acesso a métodos de CRUD e outras operações no banco de dados
 * associadas à entidade {@link Order}.
 * <p>
 * Além dos métodos herdados de {@link JpaRepository}, a interface define consultas personalizadas
 * para buscar pedidos com base em critérios específicos, como data de entrega, nome do cliente e estado do pedido.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * Retorna uma página de pedidos com a data de entrega após a data fornecida.
     * A consulta usa o gráfico de entidades {@link Order.ENTITY_GRAPTH_BRIEF} para carregar
     * os atributos associados, como o cliente e o local de retirada.
     * 
     * @param filterDate A data a ser filtrada.
     * @param pageable As informações de paginação.
     * @return Uma página de pedidos com data de entrega após a data fornecida.
     */
    @EntityGraph(value = Order.ENTITY_GRAPTH_BRIEF, type = EntityGraphType.LOAD)
    Page<Order> findByDueDateAfter(LocalDate filterDate, Pageable pageable);

    /**
     * Retorna uma página de pedidos com base no nome do cliente, ignorando maiúsculas e minúsculas.
     * A consulta usa o gráfico de entidades {@link Order.ENTITY_GRAPTH_BRIEF}.
     * 
     * @param searchQuery O nome do cliente a ser pesquisado.
     * @param pageable As informações de paginação.
     * @return Uma página de pedidos com base no nome do cliente.
     */
    @EntityGraph(value = Order.ENTITY_GRAPTH_BRIEF, type = EntityGraphType.LOAD)
    Page<Order> findByCustomerFullNameContainingIgnoreCase(String searchQuery, Pageable pageable);

    /**
     * Retorna uma página de pedidos com base no nome do cliente e na data de entrega após uma data fornecida.
     * A consulta usa o gráfico de entidades {@link Order.ENTITY_GRAPTH_BRIEF}.
     * 
     * @param searchQuery O nome do cliente a ser pesquisado.
     * @param dueDate A data de entrega para filtrar os pedidos.
     * @param pageable As informações de paginação.
     * @return Uma página de pedidos com base no nome do cliente e na data de entrega.
     */
    @EntityGraph(value = Order.ENTITY_GRAPTH_BRIEF, type = EntityGraphType.LOAD)
    Page<Order> findByCustomerFullNameContainingIgnoreCaseAndDueDateAfter(String searchQuery, LocalDate dueDate, Pageable pageable);

    /**
     * Retorna todos os pedidos no banco de dados. A consulta usa o gráfico de entidades {@link Order.ENTITY_GRAPTH_BRIEF}.
     * 
     * @return Uma lista de todos os pedidos.
     */
    @Override
    @EntityGraph(value = Order.ENTITY_GRAPTH_BRIEF, type = EntityGraphType.LOAD)
    List<Order> findAll();

    /**
     * Retorna todos os pedidos paginados. A consulta usa o gráfico de entidades {@link Order.ENTITY_GRAPTH_BRIEF}.
     * 
     * @param pageable As informações de paginação.
     * @return Uma página de pedidos.
     */
    @Override
    @EntityGraph(value = Order.ENTITY_GRAPTH_BRIEF, type = EntityGraphType.LOAD)
    Page<Order> findAll(Pageable pageable);

    /**
     * Retorna uma lista de resumos de pedidos com base na data de entrega maior ou igual à fornecida.
     * 
     * @param dueDate A data de entrega para filtrar os pedidos.
     * @return Uma lista de resumos de pedidos.
     */
    @EntityGraph(value = Order.ENTITY_GRAPTH_BRIEF, type = EntityGraphType.LOAD)
    List<OrderSummary> findByDueDateGreaterThanEqual(LocalDate dueDate);

    /**
     * Retorna um pedido com base no ID fornecido. A consulta usa o gráfico de entidades {@link Order.ENTITY_GRAPTH_FULL}.
     * 
     * @param id O ID do pedido a ser buscado.
     * @return Um pedido, caso encontrado.
     */
    @Override
    @EntityGraph(value = Order.ENTITY_GRAPTH_FULL, type = EntityGraphType.LOAD)
    Optional<Order> findById(Long id);

    /**
     * Conta o número de pedidos com data de entrega após a data fornecida.
     * 
     * @param dueDate A data de entrega para filtrar os pedidos.
     * @return O número de pedidos com data de entrega após a data fornecida.
     */
    long countByDueDateAfter(LocalDate dueDate);

    /**
     * Conta o número de pedidos com o nome do cliente contendo a string fornecida, ignorando maiúsculas e minúsculas.
     * 
     * @param searchQuery O nome do cliente a ser pesquisado.
     * @return O número de pedidos com o nome do cliente contendo a string fornecida.
     */
    long countByCustomerFullNameContainingIgnoreCase(String searchQuery);

    /**
     * Conta o número de pedidos com o nome do cliente contendo a string fornecida e a data de entrega após a data fornecida.
     * 
     * @param searchQuery O nome do cliente a ser pesquisado.
     * @param dueDate A data de entrega para filtrar os pedidos.
     * @return O número de pedidos com o nome do cliente e a data de entrega após a data fornecida.
     */
    long countByCustomerFullNameContainingIgnoreCaseAndDueDateAfter(String searchQuery, LocalDate dueDate);

    /**
     * Conta o número de pedidos com a data de entrega fornecida.
     * 
     * @param dueDate A data de entrega para filtrar os pedidos.
     * @return O número de pedidos com a data de entrega fornecida.
     */
    long countByDueDate(LocalDate dueDate);

    /**
     * Conta o número de pedidos com a data de entrega fornecida e com estado pertencente a um conjunto de estados.
     * 
     * @param dueDate A data de entrega para filtrar os pedidos.
     * @param state Os estados dos pedidos a serem filtrados.
     * @return O número de pedidos com a data de entrega e o estado fornecidos.
     */
    long countByDueDateAndStateIn(LocalDate dueDate, Collection<OrderState> state);

    /**
     * Conta o número de pedidos com o estado fornecido.
     * 
     * @param state O estado do pedido a ser filtrado.
     * @return O número de pedidos com o estado fornecido.
     */
    long countByState(OrderState state);

    /**
     * Conta o número de entregas por mês e estado de pedido.
     * 
     * @param orderState O estado do pedido.
     * @param year O ano para o qual as entregas devem ser contadas.
     * @return Uma lista de objetos representando as entregas por mês.
     */
    @Query("SELECT month(dueDate) as month, count(*) as deliveries FROM OrderInfo o where o.state=?1 and year(dueDate)=?2 group by month(dueDate)")
    List<Object[]> countPerMonth(OrderState orderState, int year);

    /**
     * Retorna o total de vendas por mês nos últimos três anos.
     * 
     * @param orderState O estado do pedido.
     * @param year O ano atual.
     * @return Uma lista de objetos representando o total de vendas por mês nos últimos três anos.
     */
    @Query("SELECT year(o.dueDate) as y, month(o.dueDate) as m, sum(oi.quantity*p.price) as deliveries FROM OrderInfo o JOIN o.items oi JOIN oi.product p where o.state=?1 and year(o.dueDate)<=?2 AND year(o.dueDate)>=(?2-3) group by year(o.dueDate), month(o.dueDate) order by y desc, month(o.dueDate)")
    List<Object[]> sumPerMonthLastThreeYears(OrderState orderState, int year);

    /**
     * Conta o número de entregas por dia, com base no estado do pedido, ano e mês.
     * 
     * @param orderState O estado do pedido.
     * @param year O ano.
     * @param month O mês.
     * @return Uma lista de objetos representando o número de entregas por dia.
     */
    @Query("SELECT day(dueDate) as day, count(*) as deliveries FROM OrderInfo o where o.state=?1 and year(dueDate)=?2 and month(dueDate)=?3 group by day(dueDate)")
    List<Object[]> countPerDay(OrderState orderState, int year, int month);

    /**
     * Conta o número de entregas por produto em um mês específico.
     * 
     * @param orderState O estado do pedido.
     * @param year O ano.
     * @param month O mês.
     * @return Uma lista de objetos representando o número de entregas por produto.
     */
    @Query("SELECT sum(oi.quantity), p FROM OrderInfo o JOIN o.items oi JOIN oi.product p WHERE o.state=?1 AND year(o.dueDate)=?2 AND month(o.dueDate)=?3 GROUP BY p.id ORDER BY p.id")
    List<Object[]> countPerProduct(OrderState orderState, int year, int month);
}
