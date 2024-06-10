package com.example.auctionservice.exception;

import com.example.shared.exceptionhandling.exception.BaseUnexpectedException;

public class FailSaveImagesException extends BaseUnexpectedException {
    public FailSaveImagesException(String message, Exception ex) {
        super(message, ex, "Ошибка сервера, повторите попытку позже");
    }
}
