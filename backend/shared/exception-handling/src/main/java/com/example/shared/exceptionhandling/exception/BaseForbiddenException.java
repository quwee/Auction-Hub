package com.example.shared.exceptionhandling.exception;

public class BaseForbiddenException extends RuntimeException {
    public BaseForbiddenException(String message) {
        super(message);
    }
}