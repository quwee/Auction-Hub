package com.example.auctionservice.exception;

import com.example.shared.exceptionhandling.exception.BaseBadRequestException;
import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.Collections;
import java.util.List;

public class IncorrectImagesNumberException extends BaseBadRequestException {
    public IncorrectImagesNumberException(String message) {
        super(message, Collections.emptyList());

        var fieldMessage = "Количество фото должно быть от 1 до 3";
        var errors = List.of(new FieldValidationError("images", fieldMessage));
        super.setFieldErrors(errors);
    }
}
