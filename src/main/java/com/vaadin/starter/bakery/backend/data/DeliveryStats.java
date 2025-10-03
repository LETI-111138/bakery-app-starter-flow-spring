package com.vaadin.starter.bakery.backend.data;

/**
 * A classe {@link DeliveryStats} armazena estatísticas relacionadas às entregas no sistema.
 * Ela contém informações sobre as entregas realizadas, pedidos que devem ser entregues hoje ou amanhã,
 * e pedidos que não estão disponíveis para entrega.
 * <p>
 * As estatísticas ajudam a fornecer uma visão geral da situação das entregas no sistema, incluindo os pedidos
 * entregues hoje, os pedidos pendentes para hoje e amanhã, e os pedidos novos ou não disponíveis.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public class DeliveryStats {

    /**
     * O número de pedidos entregues hoje.
     */
    private int deliveredToday;

    /**
     * O número de pedidos programados para entrega hoje.
     */
    private int dueToday;

    /**
     * O número de pedidos programados para entrega amanhã.
     */
    private int dueTomorrow;

    /**
     * O número de pedidos que não estão disponíveis para entrega hoje.
     */
    private int notAvailableToday;

    /**
     * O número de novos pedidos.
     */
    private int newOrders;

    /**
     * Retorna o número de pedidos entregues hoje.
     * 
     * @return O número de pedidos entregues hoje.
     */
    public int getDeliveredToday() {
        return deliveredToday;
    }

    /**
     * Define o número de pedidos entregues hoje.
     * 
     * @param deliveredToday O número de pedidos entregues hoje.
     */
    public void setDeliveredToday(int deliveredToday) {
        this.deliveredToday = deliveredToday;
    }

    /**
     * Retorna o número de pedidos programados para entrega hoje.
     * 
     * @return O número de pedidos programados para entrega hoje.
     */
    public int getDueToday() {
        return dueToday;
    }

    /**
     * Define o número de pedidos programados para entrega hoje.
     * 
     * @param dueToday O número de pedidos programados para entrega hoje.
     */
    public void setDueToday(int dueToday) {
        this.dueToday = dueToday;
    }

    /**
     * Retorna o número de pedidos programados para entrega amanhã.
     * 
     * @return O número de pedidos programados para entrega amanhã.
     */
    public int getDueTomorrow() {
        return dueTomorrow;
    }

    /**
     * Define o número de pedidos programados para entrega amanhã.
     * 
     * @param dueTomorrow O número de pedidos programados para entrega amanhã.
     */
    public void setDueTomorrow(int dueTomorrow) {
        this.dueTomorrow = dueTomorrow;
    }

    /**
     * Retorna o número de pedidos que não estão disponíveis para entrega hoje.
     * 
     * @return O número de pedidos não disponíveis para entrega hoje.
     */
    public int getNotAvailableToday() {
        return notAvailableToday;
    }

    /**
     * Define o número de pedidos que não estão disponíveis para entrega hoje.
     * 
     * @param notAvailableToday O número de pedidos não disponíveis para entrega hoje.
     */
    public void setNotAvailableToday(int notAvailableToday) {
        this.notAvailableToday = notAvailableToday;
    }

    /**
     * Retorna o número de novos pedidos.
     * 
     * @return O número de novos pedidos.
     */
    public int getNewOrders() {
        return newOrders;
    }

    /**
     * Define o número de novos pedidos.
     * 
     * @param newOrders O número de novos pedidos.
     */
    public void setNewOrders(int newOrders) {
        this.newOrders = newOrders;
    }
}
