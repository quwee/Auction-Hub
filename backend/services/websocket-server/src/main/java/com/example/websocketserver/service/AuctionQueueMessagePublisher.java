package com.example.websocketserver.service;

import com.example.shared.dto.auction.AuctionCompleteDto;
import com.example.shared.dto.bid.BidResponseDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;

public interface AuctionQueueMessagePublisher {

    void publishBid(BidResponseDto bidResponseDto);

    void publishChatMessage(ChatMessageResponseDto chatMessageResponseDto);

    void publishAuctionComplete(AuctionCompleteDto auctionCompleteDto);

}
