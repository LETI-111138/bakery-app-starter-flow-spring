package com.vaadin.starter.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * A classe {@link PickupLocation} representa um local de retirada de um pedido no sistema.
 * Um local de retirada pode ser uma loja, um ponto de coleta ou outro tipo de localização
 * onde os clientes retiram seus pedidos.
 * <p>
 * A classe é mapeada para a tabela {@code pickup_location} no banco de dados e contém o nome do local.
 * O nome é único e não pode ser nulo ou em branco.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Entity
public class PickupLocation extends AbstractEntity {

    /**
     * O nome do local de retirada. Este campo é obrigatório, deve ter no máximo 255 caracteres
     * e deve ser único no banco de dados.
     */
    @Size(max = 255)
    @NotBlank
    @Column(unique = true)
    private String name;

    /**
     * Retorna o nome do local de retirada.
     * 
     * @return O nome do local de retirada.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do local de retirada.
     * 
     * @param name O nome do local de retirada.
     */
    public void setName(String name) {
        this.name = name;
    }
}
