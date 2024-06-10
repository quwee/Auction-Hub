package com.example.shared.exceptionhandling.exception;

import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.List;

public class BaseUnauthorizedException extends BaseException {

    public BaseUnauthorizedException(String debugMessage, String responseMessage) {
        super(debugMessage, responseMessage);
    }

    public BaseUnauthorizedException(String debugMessage, Throwable cause, String responseMessage) {
        super(debugMessage, cause, responseMessage);
    }

    public BaseUnauthorizedException(String debugMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, fieldErrors);
    }

    public BaseUnauthorizedException(String debugMessage, Throwable cause, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause, fieldErrors);
    }

    public BaseUnauthorizedException(String debugMessage, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, responseMessage, fieldErrors);
    }

    public BaseUnauthorizedException(String debugMessage, Throwable cause, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause, responseMessage, fieldErrors);
    }
}