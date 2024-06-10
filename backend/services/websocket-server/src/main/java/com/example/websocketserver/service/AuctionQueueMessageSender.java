package com.example.websocketserver.service;

import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.chat.ChatMessageRequestDto;

public interface AuctionQueueMessageSender {

    void sendBidMessage(BidRequestDto bidRequestDto);

    void sendChatMessage(ChatMessageRequestDto chatMessageRequestDto);

}
