package com.josiassantos.desafio_anota_ai.commons.exceptions;

public class GenericException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
