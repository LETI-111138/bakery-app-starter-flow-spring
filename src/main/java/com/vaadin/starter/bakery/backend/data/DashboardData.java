package com.vaadin.starter.bakery.backend.data;

import java.util.LinkedHashMap;
import java.util.List;

import com.vaadin.starter.bakery.backend.data.entity.Product;

/**
 * A classe {@link DashboardData} armazena dados relacionados ao painel de controle do sistema de pedidos.
 * Ela contém informações estatísticas sobre entregas e vendas, incluindo dados mensais e anuais, e informações
 * sobre as entregas de produtos.
 * <p>
 * Esta classe é utilizada para exibir as estatísticas e os dados de desempenho do sistema no painel de controle.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public class DashboardData {

    /**
     * Estatísticas gerais sobre as entregas, como o total de entregas feitas, entregas concluídas, etc.
     */
    private DeliveryStats deliveryStats;

    /**
     * Lista de números representando as entregas feitas neste mês.
     */
    private List<Number> deliveriesThisMonth;

    /**
     * Lista de números representando as entregas feitas neste ano.
     */
    private List<Number> deliveriesThisYear;

    /**
     * Matrizes de números representando as vendas por mês. Cada linha da matriz contém dados de vendas
     * para um mês específico.
     */
    private Number[][] salesPerMonth;

    /**
     * Mapa contendo o número de entregas feitas para cada produto. A chave é o produto, e o valor é o número
     * de entregas feitas desse produto.
     */
    private LinkedHashMap<Product, Integer> productDeliveries;

    /**
     * Retorna as estatísticas de entregas.
     * 
     * @return As estatísticas de entregas.
     */
    public DeliveryStats getDeliveryStats() {
        return deliveryStats;
    }

    /**
     * Define as estatísticas de entregas.
     * 
     * @param deliveryStats As estatísticas de entregas a serem definidas.
     */
    public void setDeliveryStats(DeliveryStats deliveryStats) {
        this.deliveryStats = deliveryStats;
    }

    /**
     * Retorna a lista de entregas feitas neste mês.
     * 
     * @return A lista de entregas feitas neste mês.
     */
    public List<Number> getDeliveriesThisMonth() {
        return deliveriesThisMonth;
    }

    /**
     * Define a lista de entregas feitas neste mês.
     * 
     * @param deliveriesThisMonth A lista de entregas a serem definidas para este mês.
     */
    public void setDeliveriesThisMonth(List<Number> deliveriesThisMonth) {
        this.deliveriesThisMonth = deliveriesThisMonth;
    }

    /**
     * Retorna a lista de entregas feitas neste ano.
     * 
     * @return A lista de entregas feitas neste ano.
     */
    public List<Number> getDeliveriesThisYear() {
        return deliveriesThisYear;
    }

    /**
     * Define a lista de entregas feitas neste ano.
     * 
     * @param deliveriesThisYear A lista de entregas a serem definidas para este ano.
     */
    public void setDeliveriesThisYear(List<Number> deliveriesThisYear) {
        this.deliveriesThisYear = deliveriesThisYear;
    }

    /**
     * Define as vendas por mês.
     * 
     * @param salesPerMonth As vendas por mês a serem definidas.
     */
    public void setSalesPerMonth(Number[][] salesPerMonth) {
        this.salesPerMonth = salesPerMonth;
    }

    /**
     * Retorna as vendas de um mês específico.
     * 
     * @param i O índice do mês desejado (0 para o primeiro mês, 1 para o segundo, etc.).
     * @return As vendas do mês especificado.
     */
    public Number[] getSalesPerMonth(int i) {
        return salesPerMonth[i];
    }

    /**
     * Retorna o mapa de entregas de produtos.
     * 
     * @return O mapa contendo o número de entregas feitas para cada produto.
     */
    public LinkedHashMap<Product, Integer> getProductDeliveries() {
        return productDeliveries;
    }

    /**
     * Define o mapa de entregas de produtos.
     * 
     * @param productDeliveries O mapa de entregas de produtos a ser definido.
     */
    public void setProductDeliveries(LinkedHashMap<Product, Integer> productDeliveries) {
        this.productDeliveries = productDeliveries;
    }
}
