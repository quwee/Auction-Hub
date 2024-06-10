package com.example.auctionservice.service.impl;

import com.example.auctionservice.service.AuctionQueueMessageProcessor;
import com.example.auctionservice.service.BidService;
import com.example.auctionservice.service.ChatMessageService;
import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.bid.BidResponseDto;
import com.example.shared.dto.chat.ChatMessageRequestDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import static com.example.shared.messagequeueconfig.constants.AppMessageQueueConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionQueueMessageProcessorImpl implements AuctionQueueMessageProcessor {

    private final AmqpTemplate amqpTemplate;
    private final BidService bidService;
    private final ChatMessageService chatMessageService;

    @Override
    @RabbitListener(queues = BID_PROCESS_Q_NAME)
    public void processBidMessage(@Payload @Valid BidRequestDto bidRequestDto) {
        log.debug("processBidMessage: bidRequestDto: {}", bidRequestDto);

        BidResponseDto bidResponseDto;

        try {
            if(bidRequestDto.getId() == null) {
                bidResponseDto = bidService.createBid(bidRequestDto);
            }
            else {
                bidResponseDto = bidService.raiseBid(bidRequestDto);
            }
            amqpTemplate.convertAndSend(BID_PUBLISH_Q_NAME, bidResponseDto);
        } catch (Exception ex) {
            log.warn("processBidMessage: Exception due message processing: {}", ex.getMessage());
        }
    }

    @Override
    @RabbitListener(queues = CHAT_PROCESS_Q_NAME)
    @SendTo(CHAT_PUBLISH_Q_NAME)
    public void processChatMessage(
            @Payload @Valid ChatMessageRequestDto chatMessageRequestDto
    ) {
        log.debug("processChatMessage: chatMessageRequestDto: {}", chatMessageRequestDto);

        ChatMessageResponseDto chatMessageResponseDto;

        try {
            chatMessageResponseDto = chatMessageService.createChatMessage(chatMessageRequestDto);
            amqpTemplate.convertAndSend(CHAT_PUBLISH_Q_NAME, chatMessageResponseDto);
        } catch (Exception ex) {
            log.warn("processChatMessage: Exception due message processing: {}", ex.getMessage());
        }
    }

}
