package com.example.userservice.exception;

import com.example.shared.exceptionhandling.exception.BaseNotFoundException;

public class UserNotFoundException extends BaseNotFoundException {
    public UserNotFoundException(String message) {
        super(message, "Пользователь с такой почтой не найден");
    }
}
