package br.ufms.cpcx.trabalho1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException {

    private static final long serialVersionUID = -37103013701060410L;

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

    public HashMap<String, String> toJson() {
        HashMap<String, String> map = new HashMap<>();
        map.put("error", getClass().getName());
        map.put("mensagem", getMessage());

        return map;
    }
}
