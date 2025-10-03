package com.vaadin.starter.bakery.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.vaadin.starter.bakery.backend.data.DashboardData;
import com.vaadin.starter.bakery.backend.data.DeliveryStats;
import com.vaadin.starter.bakery.backend.data.OrderState;
import com.vaadin.starter.bakery.backend.data.entity.Order;
import com.vaadin.starter.bakery.backend.data.entity.OrderSummary;
import com.vaadin.starter.bakery.backend.data.entity.Product;
import com.vaadin.starter.bakery.backend.data.entity.User;
import com.vaadin.starter.bakery.backend.repositories.OrderRepository;

/**
 * O serviço {@link OrderService} fornece operações de lógica de negócios para gerenciar pedidos no sistema.
 * Ele oferece métodos para salvar, excluir e modificar pedidos, além de consultas para buscar pedidos com
 * base em filtros, como nome do cliente e data de entrega. O serviço também fornece dados estatísticos sobre
 * as entregas e vendas.
 * <p>
 * O serviço é responsável por calcular e retornar informações sobre entregas, vendas por mês, vendas por
 * produto, e outros dados agregados para o painel de controle (dashboard).
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Service
public class OrderService implements CrudService<Order> {

    private final OrderRepository orderRepository;

    /**
     * Construtor que injeta a dependência do repositório de pedidos.
     * 
     * @param orderRepository O repositório de pedidos.
     */
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        super();
        this.orderRepository = orderRepository;
    }

    /**
     * Conjunto imutável de estados de pedidos que não estão disponíveis para entrega.
     */
    private static final Set<OrderState> notAvailableStates = Collections.unmodifiableSet(
            EnumSet.complementOf(EnumSet.of(OrderState.DELIVERED, OrderState.READY, OrderState.CANCELLED)));

    /**
     * Salva um pedido, criando ou atualizando, dependendo se o ID é fornecido.
     * Este método também permite que um método adicional seja usado para preencher ou modificar o pedido.
     * 
     * @param currentUser O usuário atual que está fazendo a alteração.
     * @param id O ID do pedido a ser salvo (pode ser nulo para criar um novo pedido).
     * @param orderFiller Um método de preenchimento usado para modificar o pedido.
     * @return O pedido salvo.
     */
    @Transactional(rollbackOn = Exception.class)
    public Order saveOrder(User currentUser, Long id, BiConsumer<User, Order> orderFiller) {
        Order order;
        if (id == null) {
            order = new Order(currentUser);
        } else {
            order = load(id);
        }
        orderFiller.accept(currentUser, order);
        return orderRepository.save(order);
    }

    /**
     * Salva um pedido sem precisar de modificações adicionais.
     * 
     * @param order O pedido a ser salvo.
     * @return O pedido salvo.
     */
    @Transactional(rollbackOn = Exception.class)
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * Adiciona um comentário ao histórico de um pedido e salva o pedido.
     * 
     * @param currentUser O usuário atual que adiciona o comentário.
     * @param order O pedido ao qual o comentário será adicionado.
     * @param comment O comentário a ser adicionado ao histórico.
     * @return O pedido com o comentário adicionado.
     */
    @Transactional(rollbackOn = Exception.class)
    public Order addComment(User currentUser, Order order, String comment) {
        order.addHistoryItem(currentUser, comment);
        return orderRepository.save(order);
    }

    /**
     * Retorna uma página de pedidos com base em um filtro opcional de nome de cliente e data de entrega.
     * A pesquisa é realizada considerando o nome do cliente e/ou a data de entrega.
     * 
     * @param optionalFilter O filtro opcional para o nome do cliente.
     * @param optionalFilterDate O filtro opcional para a data de entrega.
     * @param pageable As informações de paginação.
     * @return Uma página de pedidos que correspondem ao filtro fornecido.
     */
    public Page<Order> findAnyMatchingAfterDueDate(Optional<String> optionalFilter,
            Optional<LocalDate> optionalFilterDate, Pageable pageable) {
        if (optionalFilter.isPresent() && !optionalFilter.get().isEmpty()) {
            if (optionalFilterDate.isPresent()) {
                return orderRepository.findByCustomerFullNameContainingIgnoreCaseAndDueDateAfter(
                        optionalFilter.get(), optionalFilterDate.get(), pageable);
            } else {
                return orderRepository.findByCustomerFullNameContainingIgnoreCase(optionalFilter.get(), pageable);
            }
        } else {
            if (optionalFilterDate.isPresent()) {
                return orderRepository.findByDueDateAfter(optionalFilterDate.get(), pageable);
            } else {
                return orderRepository.findAll(pageable);
            }
        }
    }

    /**
     * Retorna uma lista de resumos de pedidos com data de entrega a partir de hoje.
     * 
     * @return Uma lista de resumos de pedidos com entrega a partir de hoje.
     */
    @Transactional
    public List<OrderSummary> findAnyMatchingStartingToday() {
        return orderRepository.findByDueDateGreaterThanEqual(LocalDate.now());
    }

    /**
     * Conta o número de pedidos que correspondem ao filtro fornecido.
     * 
     * @param optionalFilter O filtro opcional para o nome do cliente.
     * @param optionalFilterDate O filtro opcional para a data de entrega.
     * @return O número de pedidos que correspondem ao filtro fornecido.
     */
    public long countAnyMatchingAfterDueDate(Optional<String> optionalFilter, Optional<LocalDate> optionalFilterDate) {
        if (optionalFilter.isPresent() && optionalFilterDate.isPresent()) {
            return orderRepository.countByCustomerFullNameContainingIgnoreCaseAndDueDateAfter(optionalFilter.get(),
                    optionalFilterDate.get());
        } else if (optionalFilter.isPresent()) {
            return orderRepository.countByCustomerFullNameContainingIgnoreCase(optionalFilter.get());
        } else if (optionalFilterDate.isPresent()) {
            return orderRepository.countByDueDateAfter(optionalFilterDate.get());
        } else {
            return orderRepository.count();
        }
    }

    /**
     * Retorna as estatísticas de entrega para o sistema, incluindo os pedidos entregues hoje,
     * os pedidos pendentes para hoje e amanhã, e o número de novos pedidos.
     * 
     * @return As estatísticas de entrega.
     */
    private DeliveryStats getDeliveryStats() {
        DeliveryStats stats = new DeliveryStats();
        LocalDate today = LocalDate.now();
        stats.setDueToday((int) orderRepository.countByDueDate(today));
        stats.setDueTomorrow((int) orderRepository.countByDueDate(today.plusDays(1)));
        stats.setDeliveredToday((int) orderRepository.countByDueDateAndStateIn(today,
                Collections.singleton(OrderState.DELIVERED)));
        stats.setNotAvailableToday((int) orderRepository.countByDueDateAndStateIn(today, notAvailableStates));
        stats.setNewOrders((int) orderRepository.countByState(OrderState.NEW));
        return stats;
    }

    /**
     * Retorna os dados do painel de controle (dashboard), incluindo as estatísticas de entrega, entregas diárias,
     * entregas mensais, vendas por mês e entregas por produto.
     * 
     * @param month O mês para o qual as entregas devem ser contadas.
     * @param year O ano para o qual as entregas devem ser contadas.
     * @return Os dados do painel de controle.
     */
    public DashboardData getDashboardData(int month, int year) {
        DashboardData data = new DashboardData();
        data.setDeliveryStats(getDeliveryStats());
        data.setDeliveriesThisMonth(getDeliveriesPerDay(month, year));
        data.setDeliveriesThisYear(getDeliveriesPerMonth(year));

        Number[][] salesPerMonth = new Number[3][12];
        data.setSalesPerMonth(salesPerMonth);
        List<Object[]> sales = orderRepository.sumPerMonthLastThreeYears(OrderState.DELIVERED, year);

        for (Object[] salesData : sales) {
            int y = year - (int) salesData[0];
            int m = (int) salesData[1] - 1;
            if (y == 0 && m == month - 1) {
                continue;
            }
            long count = (long) salesData[2];
            salesPerMonth[y][m] = count;
        }

        LinkedHashMap<Product, Integer> productDeliveries = new LinkedHashMap<>();
        data.setProductDeliveries(productDeliveries);
        for (Object[] result : orderRepository.countPerProduct(OrderState.DELIVERED, year, month)) {
            int sum = ((Long) result[0]).intValue();
            Product p = (Product) result[1];
            productDeliveries.put(p, sum);
        }

        return data;
    }

    /**
     * Retorna o número de entregas por dia em um mês específico.
     * 
     * @param month O mês para o qual as entregas devem ser contadas.
     * @param year O ano para o qual as entregas devem ser contadas.
     * @return Uma lista de entregas por dia no mês fornecido.
     */
    private List<Number> getDeliveriesPerDay(int month, int year) {
        int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
        return flattenAndReplaceMissingWithNull(daysInMonth,
                orderRepository.countPerDay(OrderState.DELIVERED, year, month));
    }

    /**
     * Retorna o número de entregas por mês no ano fornecido.
     * 
     * @param year O ano para o qual as entregas devem ser contadas.
     * @return Uma lista de entregas por mês no ano fornecido.
     */
    private List<Number> getDeliveriesPerMonth(int year) {
        return flattenAndReplaceMissingWithNull(12, orderRepository.countPerMonth(OrderState.DELIVERED, year));
    }

    /**
     * Auxilia na transformação de uma lista de entregas, preenchendo valores ausentes com {@code null}.
     * 
     * @param length O comprimento da lista.
     * @param list A lista de entregas.
     * @return A lista transformada, com valores ausentes preenchidos com {@code null}.
     */
    private List<Number> flattenAndReplaceMissingWithNull(int length, List<Object[]> list) {
        List<Number> counts = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            counts.add(null);
        }

        for (Object[] result : list) {
            counts.set((Integer) result[0] - 1, (Number) result[1]);
        }
        return counts;
    }

    /**
     * Retorna o repositório de pedidos para realizar operações CRUD.
     * 
     * @return O repositório de pedidos.
     */
    @Override
    public JpaRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    /**
     * Cria um novo pedido com as configurações padrão.
     * 
     * @param currentUser O usuário que está criando o pedido.
     * @return O novo pedido.
     */
    @Override
    @Transactional
    public Order createNew(User currentUser) {
        Order order = new Order(currentUser);
        order.setDueTime(LocalTime.of(16, 0));
        order.setDueDate(LocalDate.now());
        return order;
    }
}
