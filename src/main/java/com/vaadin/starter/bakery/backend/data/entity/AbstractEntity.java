package com.vaadin.starter.bakery.backend.data.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

/**
 * A classe {@link AbstractEntity} é a superclasse abstrata de todas as entidades persistentes no sistema.
 * Ela fornece os campos básicos {@code id} e {@code version} que são comuns a todas as entidades
 * no banco de dados. Essa classe também implementa os métodos {@code equals} e {@code hashCode} com base
 * no {@code id} e {@code version}, garantindo comparações e operações de hash corretas.
 * 
 * As entidades que precisam de persistência devem estender essa classe e herdar seus comportamentos.
 * <p>
 * A classe é anotada com {@link MappedSuperclass}, o que significa que os seus campos serão mapeados
 * para as tabelas das subclasses, mas não será criada uma tabela para ela diretamente no banco de dados.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    /**
     * Retorna o identificador único da entidade.
     * 
     * @return O identificador {@code id} da entidade.
     */
    public Long getId() {
        return id;
    }

    /**
     * Retorna a versão da entidade. Usado para controle de concorrência otimista no banco de dados.
     * 
     * @return A versão {@code version} da entidade.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Calcula o código hash da entidade com base no {@code id} e na {@code version}.
     * <p>
     * Esse método é importante para garantir que a classe funcione corretamente com coleções que dependem
     * de hashing, como {@link java.util.HashMap}.
     * </p>
     * 
     * @return O código hash calculado com base nos campos {@code id} e {@code version}.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, version);
    }

    /**
     * Compara a entidade atual com outro objeto.
     * <p>
     * A comparação é feita com base nos campos {@code id} e {@code version}, garantindo que duas entidades
     * com os mesmos valores para esses campos sejam consideradas iguais.
     * </p>
     * 
     * @param o O objeto a ser comparado.
     * @return {@code true} se os objetos forem iguais; {@code false} caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return version == that.version &&
                Objects.equals(id, that.id);
    }
}
