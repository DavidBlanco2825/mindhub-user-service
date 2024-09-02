package com.example.userservice.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.userservice.commons.Constants.EMAIL_ALREADY_EXISTS;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public Mono<ResponseEntity<ErrorResponse>> handleResourceNotFoundException(UserNotFoundException unfe, ServerWebExchange exchange) {

        ErrorResponse errorDetails = getErrorDetails(unfe.getMessage(), exchange);

        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails));
    }

    @ExceptionHandler({BadRequestException.class})
    public Mono<ResponseEntity<ErrorResponse>> handleBadRequestException(BadRequestException bre, ServerWebExchange exchange) {
        String errorMessage = extractErrorMessage(bre);

        ErrorResponse errorDetails = getErrorDetails(errorMessage, exchange);

        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails));
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public Mono<ResponseEntity<ErrorResponse>> handleDataAlreadyExistsException(DataAlreadyExistsException daee, ServerWebExchange exchange) {

        ErrorResponse errorDetails = getErrorDetails(daee.getMessage(), exchange);

        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public Mono<ResponseEntity<ErrorResponse>> handleDataIntegrityViolationException(DataIntegrityViolationException dive, ServerWebExchange exchange) {

        String errorMessage = "A conflict occurred: " + dive.getMostSpecificCause().getMessage();

        if (dive.getMostSpecificCause().getMessage().contains("USERS(EMAIL")) {
            errorMessage = EMAIL_ALREADY_EXISTS;
        }

        ErrorResponse errorDetails = getErrorDetails(errorMessage, exchange);

        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetails));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ErrorResponse>> handleInternalServerError(Exception ex, ServerWebExchange exchange) {

        ErrorResponse errorDetails = getErrorDetails(ex.getMessage(), exchange);

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails));
    }

    private ErrorResponse getErrorDetails(String message, ServerWebExchange exchange) {
        return new ErrorResponse(
                LocalDateTime.now(),
                message,
                exchange.getRequest().getPath().value());
    }

    private String extractErrorMessage(BadRequestException bre) {
        List<FieldError> fieldErrors = bre.getFieldErrors();
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            return fieldErrors.get(0).getDefaultMessage();
        }
        return bre.getMessage();
    }

}
