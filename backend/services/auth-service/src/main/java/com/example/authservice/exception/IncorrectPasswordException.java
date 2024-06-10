package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseBadRequestException;
import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.Collections;
import java.util.List;

public class IncorrectPasswordException extends BaseBadRequestException {
    public IncorrectPasswordException(String message) {
        super(message, Collections.emptyList());

        var fieldMessage = "Неверный пароль";
        var errors = List.of(new FieldValidationError("password", fieldMessage));
        super.setFieldErrors(errors);
    }
}
