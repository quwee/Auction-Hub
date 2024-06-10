package com.example.websocketserver.service.impl;

import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.chat.ChatMessageRequestDto;
import com.example.websocketserver.service.AuctionQueueMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import static com.example.shared.messagequeueconfig.constants.AppMessageQueueConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionQueueMessageSenderImpl implements AuctionQueueMessageSender {

    private final AmqpTemplate amqpTemplate;

    @Override
    public void sendBidMessage(BidRequestDto bidRequestDto) {
        log.debug("sendBidMessage: bidRequestDto: {}", bidRequestDto);

        amqpTemplate.convertAndSend(BID_PROCESS_Q_NAME, bidRequestDto);
    }

    @Override
    public void sendChatMessage(ChatMessageRequestDto chatMessageRequestDto) {
        log.debug("sendChatMessage: chatMessageRequestDto: {}", chatMessageRequestDto);

        amqpTemplate.convertAndSend(CHAT_PROCESS_Q_NAME, chatMessageRequestDto);
    }

}
