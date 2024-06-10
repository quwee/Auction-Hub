package com.example.auctionservice.service;

import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.bid.BidResponseDto;

import java.util.List;

public interface BidService {

    BidResponseDto createBid(BidRequestDto bidResponseDto);

    BidResponseDto raiseBid(BidRequestDto bidResponseDto);

    List<BidResponseDto> getBids(Long auctionId);

}
