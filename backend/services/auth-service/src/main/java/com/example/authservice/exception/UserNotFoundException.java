package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseNotFoundException;
import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.Collections;
import java.util.List;

public class UserNotFoundException extends BaseNotFoundException {
    public UserNotFoundException(String message) {
        super(message, Collections.emptyList());

        var fieldMessage = "Пользователь с такой почтой не найден";
        var errors = List.of(new FieldValidationError("email", fieldMessage));
        super.setFieldErrors(errors);
    }
}
