package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseConflictExistException;

public class RegistrationAlreadyConfirmException extends BaseConflictExistException {
    public RegistrationAlreadyConfirmException(String message) {
        super(message, "");

        super.setResponseMessage("Регистрация уже подтверждена");
    }
}
