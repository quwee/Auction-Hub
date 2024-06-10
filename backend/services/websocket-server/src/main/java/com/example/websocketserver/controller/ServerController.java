package com.example.websocketserver.controller;

import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.chat.ChatMessageRequestDto;
import com.example.websocketserver.service.AuctionQueueMessageSender;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ServerController {

    private final AuctionQueueMessageSender messageSender;

    @MessageMapping("/send-bid")
    public void processBid(@Payload @Valid BidRequestDto bidRequestDto) {
        messageSender.sendBidMessage(bidRequestDto);
    }

    @MessageMapping("/send-message")
    public void processChatMessage(@Payload @Valid ChatMessageRequestDto chatMessageRequestDto) {
        messageSender.sendChatMessage(chatMessageRequestDto);
    }

}
