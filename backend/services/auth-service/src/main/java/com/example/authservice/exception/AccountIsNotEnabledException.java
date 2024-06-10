package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseUnauthorizedException;
import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.Collections;
import java.util.List;

public class AccountIsNotEnabledException extends BaseUnauthorizedException {
    public AccountIsNotEnabledException(String message) {
        super(message, Collections.emptyList());

        var fieldMessage = "Аккаунт не подтвержден";
        var errors = List.of(new FieldValidationError("email", fieldMessage));
        super.setFieldErrors(errors);
    }
}
