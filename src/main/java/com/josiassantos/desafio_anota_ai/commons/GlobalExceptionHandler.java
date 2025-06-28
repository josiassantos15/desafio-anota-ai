package com.josiassantos.desafio_anota_ai.commons;

import com.josiassantos.desafio_anota_ai.commons.exceptions.GenericException;
import com.josiassantos.desafio_anota_ai.commons.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.core.exception.RetryableException;

import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorInformation> handleValidationException(ValidationException exception,
                                                                      HttpServletRequest request) {
        log.warn("Validation error: {}", exception.getMessage(), exception);
        String message = exception.getFieldName() != null && !exception.getFieldName().isBlank()
                ? exception.getFieldName() + ": " + exception.getMessage()
                : exception.getMessage();
        return ResponseEntity
                .badRequest()
                .body(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), message));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInformation> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                         HttpServletRequest request) {
        log.error("MethodArgumentNotValidException: {}", exception.getMessage(), exception);

        String field = Optional.of(exception.getBindingResult().getFieldError())
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Dados inválidos");

        return ResponseEntity
                .badRequest()
                .body(new ErrorInformation(HttpStatus.BAD_REQUEST.value(), request.getRequestURI(), field));
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorInformation> handleGenericException(GenericException exception,
                                                                   HttpServletRequest request) {
        log.error("Generic exception: {}", exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorInformation(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        request.getRequestURI(),
                        exception.getMessage())
                );
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ErrorInformation> handleRetryableException(RetryableException exception,
                                                                     HttpServletRequest request) {
        log.error("Retryable exception: {}", exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(new ErrorInformation(
                        HttpStatus.SERVICE_UNAVAILABLE.value(),
                        request.getRequestURI(),
                        "Serviço indisponível: " + exception.getMessage())
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInformation> handleUnexpectedException(Exception exception,
                                                                      HttpServletRequest request) {
        log.error("Unexpected exception: {}", exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorInformation(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        request.getRequestURI(),
                        "Erro interno do servidor")
                );
    }
}
