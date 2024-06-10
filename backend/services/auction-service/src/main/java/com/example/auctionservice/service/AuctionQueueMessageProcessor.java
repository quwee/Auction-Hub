package com.example.auctionservice.service;

import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.chat.ChatMessageRequestDto;

public interface AuctionQueueMessageProcessor {

    void processBidMessage(BidRequestDto bidRequestDto);

    void processChatMessage(ChatMessageRequestDto chatMessageRequestDto);

}
