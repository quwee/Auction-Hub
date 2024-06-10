package com.example.shared.exceptionhandling.exception;

import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.List;

public class BaseBadRequestException extends BaseException {

    public BaseBadRequestException(String debugMessage, String responseMessage) {
        super(debugMessage, responseMessage);
    }

    public BaseBadRequestException(String debugMessage, Throwable cause, String responseMessage) {
        super(debugMessage, cause, responseMessage);
    }

    public BaseBadRequestException(String debugMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, fieldErrors);
    }

    public BaseBadRequestException(String debugMessage, Throwable cause, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause, fieldErrors);
    }

    public BaseBadRequestException(String debugMessage, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, responseMessage, fieldErrors);
    }

    public BaseBadRequestException(String debugMessage, Throwable cause, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause, responseMessage, fieldErrors);
    }
}