package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseConflictExistException;
import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.Collections;
import java.util.List;

public class UserAlreadyExistException extends BaseConflictExistException {
    public UserAlreadyExistException(String message) {
        super(message, Collections.emptyList());

        var fieldMessage = "Пользователь с такой почтой уже существует";
        var errors = List.of(new FieldValidationError("email", fieldMessage));
        super.setFieldErrors(errors);
    }
}
