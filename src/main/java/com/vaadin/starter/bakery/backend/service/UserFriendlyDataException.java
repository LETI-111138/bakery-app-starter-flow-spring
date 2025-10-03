package com.vaadin.starter.bakery.backend.service;

import org.springframework.dao.DataIntegrityViolationException;

/**
 * Exceção personalizada que estende {@link DataIntegrityViolationException}, usada para sinalizar violação de integridade
 * de dados de uma maneira amigável ao usuário. Esta exceção contém uma mensagem que pode ser mostrada diretamente ao usuário.
 * <p>
 * Normalmente, essa exceção é lançada quando ocorre uma violação de dados, como tentar inserir um valor duplicado ou inválido
 * em um campo que deve ser único ou com restrições específicas, mas a mensagem gerada é voltada para o usuário final.
 * </p>
 * 
 * @author Tiago Eliseu
 * @version 1.0
 */
public class UserFriendlyDataException extends DataIntegrityViolationException {

    /**
     * Constrói a exceção com uma mensagem específica que será exibida ao usuário final.
     * 
     * @param message A mensagem a ser exibida ao usuário final, explicando o erro de forma amigável.
     */
    public UserFriendlyDataException(String message) {
        super(message);
    }
}
