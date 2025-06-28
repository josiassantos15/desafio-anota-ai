package com.josiassantos.desafio_anota_ai.commons;

import com.josiassantos.desafio_anota_ai.commons.exceptions.GenericException;
import com.josiassantos.desafio_anota_ai.commons.exceptions.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import software.amazon.awssdk.core.exception.RetryableException;

@Slf4j
@Configuration
@ControllerAdvice
@ComponentScan(basePackageClasses = {GlobalExceptionHandler.class})
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({GenericException.class})
    public ErrorInformation genericListenerException(GenericException excecao,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        log.error("Generic error: %s".formatted(excecao.getMessage()), excecao.getCause());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ErrorInformation(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getRequestURI(),
                excecao.getMessage()
        );
    }

    @ResponseBody
    @ExceptionHandler({Exception.class})
    public ErrorInformation internalListenerException(Exception exception,
                                                      HttpServletRequest request,
                                                      HttpServletResponse response) {
        log.error("Internal error: %s".formatted(exception.getMessage()), exception.getCause());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ErrorInformation(response.getStatus(), request.getRequestURI(), exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ValidationException.class})
    public ErrorInformation errorsListenerValidation(ValidationException validationException,
                                                     HttpServletRequest request,
                                                     HttpServletResponse response) {
        log.error("Validation error: %s".formatted(validationException.getMessage()), validationException.getCause());
        int badRequestStatus = HttpStatus.BAD_REQUEST.value();
        response.setStatus(badRequestStatus);
        String fieldName = validationException.getFieldName();
        String messageException = (fieldName != null && !fieldName.isBlank())
                ? fieldName + ": " + validationException.getMessage()
                : validationException.getMessage();
        return new ErrorInformation(
                badRequestStatus,
                request.getRequestURI(),
                messageException
        );
    }

    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorInformation errorListenerValidationSpring(MethodArgumentNotValidException validationException,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {
        int badRequestStatus = HttpStatus.BAD_REQUEST.value();
        log.error("Validation error: %s".formatted(validationException.getMessage()), validationException.getCause());
        response.setStatus(badRequestStatus);
        String errorMessage = validationException.getBindingResult().getFieldError().getField();
        return new ErrorInformation(
                badRequestStatus,
                request.getRequestURI(),
                errorMessage + ": " + validationException.getBindingResult().getFieldError().getDefaultMessage()
        );
    }

    @ResponseBody
    @ExceptionHandler({RetryableException.class})
    public ErrorInformation errorListenerRetry(RetryableException validationException,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        log.error("Communication error with external API (unavailable): %s".formatted(validationException.getMessage()),
                validationException.getCause());
        response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.value());
        return new ErrorInformation(
                response.getStatus(),
                request.getRequestURI(),
                "Service Unavailable: " + validationException.getMessage()
        );
    }
}

