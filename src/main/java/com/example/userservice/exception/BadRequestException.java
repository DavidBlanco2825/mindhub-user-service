package com.example.userservice.exception;

import org.springframework.validation.FieldError;

import java.util.List;

public class BadRequestException extends RuntimeException {

    private final List<FieldError> fieldErrors;

    public BadRequestException(String message) {
        super(message);
        this.fieldErrors = null;
    }

    public BadRequestException(List<FieldError> fieldErrors) {
        super(fieldErrors.toString());
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
