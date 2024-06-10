package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseBadRequestException;
import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.Collections;
import java.util.List;

public class PasswordsNotMatchException extends BaseBadRequestException {
    public PasswordsNotMatchException(String message) {
        super(message, Collections.emptyList());

        var fieldMessage = "Пароли не совпадают";
        var errors = List.of(
                new FieldValidationError("password", fieldMessage),
                new FieldValidationError("passwordConfirmation", fieldMessage)
        );
        super.setFieldErrors(errors);
    }

}
