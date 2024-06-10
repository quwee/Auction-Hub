package com.example.auctionservice.exception;

import com.example.shared.exceptionhandling.exception.BaseNotFoundException;

public class BidNotFoundException extends BaseNotFoundException {

    public BidNotFoundException(String message) {
        super(message, "Сделка не найдена");
    }


}
