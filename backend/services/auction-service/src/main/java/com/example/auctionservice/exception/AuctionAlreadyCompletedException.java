package com.example.auctionservice.exception;

import com.example.shared.exceptionhandling.exception.BaseBadRequestException;

public class AuctionAlreadyCompletedException extends BaseBadRequestException {
    public AuctionAlreadyCompletedException(String message) {
        super(message, "Аукцион уже заверешен");
    }
}
