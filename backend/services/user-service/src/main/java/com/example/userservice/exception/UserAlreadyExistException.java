package com.example.userservice.exception;

import com.example.shared.exceptionhandling.exception.BaseConflictExistException;
import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.List;

public class UserAlreadyExistException extends BaseConflictExistException {
    public UserAlreadyExistException(String message) {
        super(message, "Пользователь с данной почтой уже существует");
    }
}
