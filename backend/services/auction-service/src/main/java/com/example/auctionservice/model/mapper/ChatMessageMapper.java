package com.example.auctionservice.model.mapper;

import com.example.auctionservice.model.entity.Auction;
import com.example.auctionservice.model.entity.ChatMessage;
import com.example.shared.dto.chat.ChatMessageRequestDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    @Mapping(target = "auctionId", source = "chatMessage.auction.id")
    ChatMessageResponseDto chatMessageToChatMessageResponseDto(
            ChatMessage chatMessage,
            String firstName,
            String lastName);

    @Mapping(target = "id", ignore = true)
    ChatMessage chatMessageRequestDtoToChatMessage(
            ChatMessageRequestDto chatMessageRequestDto,
            LocalDateTime sentAt,
            Auction auction);

}
