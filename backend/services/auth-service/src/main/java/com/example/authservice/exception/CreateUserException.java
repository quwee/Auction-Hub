package com.example.authservice.exception;

import com.example.shared.exceptionhandling.exception.BaseUnexpectedException;

public class CreateUserException extends BaseUnexpectedException {

    public CreateUserException(String debugMessage, Throwable cause) {
        super(debugMessage, cause, "");

        super.setResponseMessage("Ошибка сервера, повторите попытку позже");
    }
}
