package com.example.auctionservice.util;

import com.example.auctionservice.model.entity.Bid;

import java.util.Comparator;

public class BidComparator implements Comparator<Bid> {

    @Override
    public int compare(Bid bid1, Bid bid2) {
        return bid1.getPrice().compareTo(bid2.getPrice());
    }
}
