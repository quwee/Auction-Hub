package com.example.websocketserver.service.impl;

import com.example.shared.dto.auction.AuctionCompleteDto;
import com.example.shared.dto.bid.BidResponseDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;
import com.example.websocketserver.service.AuctionQueueMessagePublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static com.example.shared.messagequeueconfig.constants.AppMessageQueueConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionQueueMessagePublisherImpl implements AuctionQueueMessagePublisher {

    private final SimpMessagingTemplate messagingTemplate;

    @Value("${app.publish-destination-prefix}")
    private String publishDestPrefix;

    @Override
    @RabbitListener(queues = BID_PUBLISH_Q_NAME)
    public void publishBid(BidResponseDto bidResponseDto) {
        log.debug("publishBid: bidResponseDto: {}", bidResponseDto);

        String dest = publishDestPrefix + "bid." + bidResponseDto.getAuctionId();

        log.debug("publishBid: dest: {}", dest);

        messagingTemplate.convertAndSend(dest, bidResponseDto);
    }

    @Override
    @RabbitListener(queues = CHAT_PUBLISH_Q_NAME)
    public void publishChatMessage(ChatMessageResponseDto chatMessageResponseDto) {
        log.debug("publishChatMessage: chatMessageResponseDto: {}", chatMessageResponseDto);

        String dest = publishDestPrefix + "chat." + chatMessageResponseDto.getAuctionId();

        log.debug("publishChatMessage: dest: {}", dest);

        messagingTemplate.convertAndSend(dest, chatMessageResponseDto);
    }

    @Override
    @RabbitListener(queues = AUCTION_CLOSE_Q_NAME)
    public void publishAuctionComplete(AuctionCompleteDto auctionCompleteDto) {
        log.debug("publishAuctionComplete: auctionCompleteDto: {}", auctionCompleteDto);

        String dest = publishDestPrefix + "complete." + auctionCompleteDto.getAuctionId();

        log.debug("publishAuctionComplete: dest: {}", dest);

        messagingTemplate.convertAndSend(dest, auctionCompleteDto);
    }
}
