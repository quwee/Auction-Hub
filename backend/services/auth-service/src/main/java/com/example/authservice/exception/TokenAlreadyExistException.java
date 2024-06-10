package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseUnexpectedException;

public class TokenAlreadyExistException extends BaseUnexpectedException {
    public TokenAlreadyExistException(String message) {
        super(message, "");

        super.setResponseMessage("Ошибка сервера, повторите попытку позже");
    }
}
