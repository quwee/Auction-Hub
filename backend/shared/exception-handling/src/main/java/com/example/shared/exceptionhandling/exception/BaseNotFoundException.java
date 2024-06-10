package com.example.shared.exceptionhandling.exception;

import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.List;

public class BaseNotFoundException extends BaseException {

    public BaseNotFoundException(String debugMessage, String responseMessage) {
        super(debugMessage, responseMessage);
    }

    public BaseNotFoundException(String debugMessage, Throwable cause, String responseMessage) {
        super(debugMessage, cause, responseMessage);
    }

    public BaseNotFoundException(String debugMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, fieldErrors);
    }

    public BaseNotFoundException(String debugMessage, Throwable cause, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause, fieldErrors);
    }

    public BaseNotFoundException(String debugMessage, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, responseMessage, fieldErrors);
    }

    public BaseNotFoundException(String debugMessage, Throwable cause, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause, responseMessage, fieldErrors);
    }
}