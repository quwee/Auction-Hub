package com.example.auctionservice.exception;

import com.example.shared.exceptionhandling.exception.BaseUnexpectedException;

public class GetUserException extends BaseUnexpectedException {
    public GetUserException(String message) {
        super(message, "Ошибка сервера, повторите попытку позже");
    }
}
