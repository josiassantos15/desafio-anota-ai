package com.josiassantos.desafio_anota_ai.commons.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class ValidationException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String fieldName;

    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
    }

    public ValidationException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }

    public ValidationException(String fieldName, String message, Throwable e) {
        super(message, e);
        this.fieldName = fieldName;
    }

    public ValidationException(String message, Throwable e) {
        super(message, e);
        this.fieldName = null;
    }

}
