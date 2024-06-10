package com.example.auctionservice.service.impl;

import com.example.auctionservice.client.UserServiceApi;
import com.example.auctionservice.exception.AuctionAlreadyCompletedException;
import com.example.auctionservice.exception.GetUserException;
import com.example.auctionservice.model.entity.Auction;
import com.example.auctionservice.model.entity.ChatMessage;
import com.example.auctionservice.model.enums.AuctionStatus;
import com.example.auctionservice.model.mapper.ChatMessageMapper;
import com.example.auctionservice.repository.ChatMessageRepo;
import com.example.auctionservice.service.AuctionService;
import com.example.auctionservice.service.ChatMessageService;
import com.example.auctionservice.service.util.AuctionServiceUtils;
import com.example.shared.dto.chat.ChatMessageRequestDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;
import com.example.shared.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepo chatMessageRepo;
    private final AuctionService auctionService;
    private final ChatMessageMapper chatMessageMapper;
    private final UserServiceApi userServiceApi;

    @Override
    @Transactional
    public ChatMessageResponseDto createChatMessage(ChatMessageRequestDto chatMessageRequestDto) {
        Auction auction = auctionService.getAuctionById(chatMessageRequestDto.getAuctionId());

        if (auction.getStatus().equals(AuctionStatus.COMPLETED)) {
            throw new AuctionAlreadyCompletedException(
                    "Auction with id %d is already completed".formatted(auction.getId()));
        }

        ChatMessage chatMessage = chatMessageMapper.chatMessageRequestDtoToChatMessage(
                chatMessageRequestDto, LocalDateTime.now(), auction);

        UserDto user;
        try {
            user = userServiceApi.getUser(chatMessage.getSenderId());
        } catch (Exception e) {
            throw new GetUserException("Fail to find chat message sender with id %d in User Service"
                    .formatted(chatMessage.getSenderId()));
        }

        chatMessage = chatMessageRepo.save(chatMessage);

        return chatMessageMapper.chatMessageToChatMessageResponseDto(
                chatMessage, user.getFirstName(), user.getLastName());
    }

}
