package com.vaadin.starter.bakery.backend.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * A classe {@link OrderItem} representa um item de um pedido, contendo informações sobre
 * o produto solicitado, a quantidade e um comentário opcional.
 * <p>
 * Esta classe é mapeada para a tabela {@code order_item} no banco de dados e possui um
 * relacionamento com o produto da ordem e o número de unidades solicitadas.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Entity
public class OrderItem extends AbstractEntity {

    /**
     * O produto associado ao item do pedido.
     * Este campo é obrigatório e mapeia para a entidade {@link Product}.
     */
    @ManyToOne
    @NotNull(message = "{bakery.pickup.product.required}")
    private Product product;

    /**
     * A quantidade do produto no pedido.
     * Este campo é obrigatório e deve ser pelo menos 1.
     */
    @Min(1)
    @NotNull
    private Integer quantity = 1;

    /**
     * Comentários adicionais sobre o item do pedido.
     * Este campo é opcional e pode conter até 255 caracteres.
     */
    @Size(max = 255)
    private String comment;

    /**
     * Retorna o produto associado ao item do pedido.
     * 
     * @return O produto associado ao item do pedido.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Define o produto associado ao item do pedido.
     * 
     * @param product O produto a ser associado ao item do pedido.
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Retorna a quantidade do produto no item do pedido.
     * 
     * @return A quantidade do produto no item do pedido.
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * Define a quantidade do produto no item do pedido.
     * 
     * @param quantity A quantidade do produto no item do pedido.
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * Retorna o comentário associado ao item do pedido.
     * 
     * @return O comentário do item do pedido.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Define o comentário associado ao item do pedido.
     * 
     * @param comment O comentário a ser associado ao item do pedido.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Calcula o preço total do item, multiplicando a quantidade pelo preço do produto.
     * Se o produto ou a quantidade forem inválidos (nulos), o preço total será 0.
     * 
     * @return O preço total do item.
     */
    public int getTotalPrice() {
        return quantity == null || product == null ? 0 : quantity * product.getPrice();
    }
}
