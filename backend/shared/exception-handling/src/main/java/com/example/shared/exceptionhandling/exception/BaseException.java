package com.example.shared.exceptionhandling.exception;

import com.example.shared.exceptionhandling.model.FieldValidationError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BaseException extends RuntimeException {

    @Getter
    @Setter
    private String responseMessage;

    @Getter
    @Setter
    private List<FieldValidationError> fieldErrors;

    public BaseException(String debugMessage, String responseMessage) {
        super(debugMessage);
        this.responseMessage = responseMessage;
    }

    public BaseException(String debugMessage, Throwable cause, String responseMessage) {
        super(debugMessage, cause);
        this.responseMessage = responseMessage;
    }

    public BaseException(String debugMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage);
        this.fieldErrors = fieldErrors;
    }

    public BaseException(String debugMessage, Throwable cause, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause);
        this.fieldErrors = fieldErrors;
    }

    public BaseException(String debugMessage, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage);
        this.responseMessage = responseMessage;
        this.fieldErrors = fieldErrors;
    }

    public BaseException(String debugMessage, Throwable cause, String responseMessage, List<FieldValidationError> fieldErrors) {
        super(debugMessage, cause);
        this.responseMessage = responseMessage;
        this.fieldErrors = fieldErrors;
    }
}
