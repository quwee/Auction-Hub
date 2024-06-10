package com.example.auctionservice.service.util;

import com.example.auctionservice.client.UserServiceApi;
import com.example.auctionservice.exception.GetUserException;
import com.example.auctionservice.model.entity.Auction;
import com.example.auctionservice.model.mapper.ChatMessageMapper;
import com.example.shared.dto.chat.ChatMessageResponseDto;
import com.example.shared.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageServiceUtils {

    private final ChatMessageMapper chatMessageMapper;
    private final UserServiceApi userServiceApi;

    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> getChatMessages(Auction auction) {
        return auction.getChatMessages()
                .stream().map(chatMessage -> {
                    UserDto user;
                    try {
                        user = userServiceApi.getUser(chatMessage.getSenderId());
                    } catch (Exception e) {
                        throw new GetUserException("Fail to find chat message sender with id %d in User Service"
                                .formatted(chatMessage.getSenderId()));
                    }
                    return chatMessageMapper.chatMessageToChatMessageResponseDto(
                            chatMessage, user.getFirstName(), user.getLastName());
                })
                .toList();
    }

}
