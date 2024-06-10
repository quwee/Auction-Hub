package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseUnauthorizedException;

public class IncorrectRefreshTokenException extends BaseUnauthorizedException {
    public IncorrectRefreshTokenException(String message) {
        super(message, "");

        super.setResponseMessage("Некорректный refresh токен");
    }
}
