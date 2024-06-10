package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseNotFoundException;

public class VerificationTokenNotFoundException extends BaseNotFoundException {
    public VerificationTokenNotFoundException(String message) {
        super(message, "");

        super.setResponseMessage("Неверный токен подтверждения");
    }
}
