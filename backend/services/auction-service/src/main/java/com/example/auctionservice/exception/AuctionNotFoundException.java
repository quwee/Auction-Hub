package com.example.auctionservice.exception;

import com.example.shared.exceptionhandling.exception.BaseNotFoundException;

public class AuctionNotFoundException extends BaseNotFoundException {

    public AuctionNotFoundException(String message) {
        super(message, "Аукцион не найден");
    }
}
