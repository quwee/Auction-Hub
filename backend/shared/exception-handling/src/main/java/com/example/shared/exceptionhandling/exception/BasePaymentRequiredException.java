package com.example.shared.exceptionhandling.exception;

public class BasePaymentRequiredException extends RuntimeException {

    public BasePaymentRequiredException(String message) {
        super(message);
    }
}