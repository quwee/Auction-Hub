package com.example.shared.exceptionhandling.exception;

import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.List;

public class BaseUnexpectedException extends BaseException {

    public BaseUnexpectedException(String debugMessage, String responseMessage) {
        super(debugMessage, responseMessage);
    }

    public BaseUnexpectedException(String debugMessage, Throwable cause, String responseMessage) {
        super(debugMessage, cause, responseMessage);
    }

    public BaseUnexpectedException(String debugMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, fieldErrors);
    }

    public BaseUnexpectedException(String debugMessage, Throwable cause, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause, fieldErrors);
    }

    public BaseUnexpectedException(String debugMessage, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, responseMessage, fieldErrors);
    }

    public BaseUnexpectedException(String debugMessage, Throwable cause, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause, responseMessage, fieldErrors);
    }
}