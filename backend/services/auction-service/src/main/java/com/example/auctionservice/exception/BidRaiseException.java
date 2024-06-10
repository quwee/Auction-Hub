package com.example.auctionservice.exception;

import com.example.shared.exceptionhandling.exception.BaseBadRequestException;
import com.example.shared.exceptionhandling.model.FieldValidationError;

import java.util.Collections;
import java.util.List;

public class BidRaiseException extends BaseBadRequestException {
    public BidRaiseException(String message) {
        super(message, Collections.emptyList());

        var fieldMessage = "Ставка меньше текущей ставки";
        var errors = List.of(new FieldValidationError("price", fieldMessage));
        super.setFieldErrors(errors);
    }


}
