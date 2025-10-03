package com.vaadin.starter.bakery.backend.data.entity.util;

import com.vaadin.starter.bakery.backend.data.entity.AbstractEntity;

/**
 * A classe {@link EntityUtil} contém utilitários para lidar com entidades no sistema.
 * Ela fornece métodos auxiliares que são usados para manipular ou obter informações relacionadas a entidades,
 * como o nome da classe da entidade.
 * <p>
 * Esta classe é final e não pode ser estendida. Todos os métodos são estáticos e podem ser acessados sem a
 * necessidade de criar uma instância da classe.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public final class EntityUtil {

    /**
     * Retorna o nome simples da classe da entidade.
     * <p>
     * Esse método retorna o nome da classe sem o pacote e sem detalhes adicionais. 
     * Ideal para quando for necessário usar o nome da classe de uma entidade de forma simples.
     * </p>
     * 
     * @param type A classe de tipo {@link AbstractEntity} para a qual se deseja obter o nome.
     * @return O nome simples da classe fornecida, sem o pacote.
     */
    public static final String getName(Class<? extends AbstractEntity> type) {
        // Todos os tipos principais de entidade possuem nomes simples de uma palavra, então isso é suficiente.
        // Métodos de metadados poderiam ser adicionados à classe, se necessário.
        return type.getSimpleName();
    }
}
