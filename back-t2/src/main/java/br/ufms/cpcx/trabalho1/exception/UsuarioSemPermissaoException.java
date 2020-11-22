package br.ufms.cpcx.trabalho1.exception;

public class UsuarioSemPermissaoException extends GenericException {
    public UsuarioSemPermissaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
