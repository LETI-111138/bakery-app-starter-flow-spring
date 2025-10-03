package com.vaadin.starter.bakery.backend.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * A classe {@link Product} representa um produto no sistema.
 * Ela contém informações sobre o nome e o preço do produto.
 * <p>
 * O preço é armazenado como um valor inteiro, multiplicado por 100 para evitar erros de arredondamento,
 * permitindo trabalhar com valores monetários de forma precisa.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@Entity
public class Product extends AbstractEntity {

    /**
     * O nome do produto. Este campo é obrigatório, deve ser único e ter no máximo 255 caracteres.
     */
    @NotBlank(message = "{bakery.name.required}")
    @Size(max = 255)
    @Column(unique = true)
    private String name;

    /**
     * O preço do produto, armazenado como um valor inteiro (preço real * 100).
     * Este campo é obrigatório e deve estar dentro do intervalo entre 0 e 100.000.
     */
    @Min(value = 0, message = "{bakery.price.limits}")
    @Max(value = 100000, message = "{bakery.price.limits}")
    private Integer price;

    /**
     * Retorna o nome do produto.
     * 
     * @return O nome do produto.
     */
    public String getName() {
        return name;
    }

    /**
     * Define o nome do produto.
     * 
     * @param name O nome do produto.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna o preço do produto.
     * O valor retornado é o preço em centavos (preço real * 100).
     * 
     * @return O preço do produto em centavos.
     */
    public Integer getPrice() {
        return price;
    }

    /**
     * Define o preço do produto.
     * O valor deve ser o preço em centavos (preço real * 100).
     * 
     * @param price O preço do produto em centavos.
     */
    public void setPrice(Integer price) {
        this.price = price;
    }

    /**
     * Retorna uma representação em string do produto, utilizando o nome do produto.
     * 
     * @return O nome do produto como string.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Compara este produto com outro objeto para verificar se são iguais.
     * A comparação é feita com base no nome e no preço do produto.
     * 
     * @param o O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais, {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Product that = (Product) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(price, that.price);
    }

    /**
     * Retorna o código hash do produto, gerado com base no nome e no preço do produto.
     * 
     * @return O código hash do produto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, price);
    }
}
