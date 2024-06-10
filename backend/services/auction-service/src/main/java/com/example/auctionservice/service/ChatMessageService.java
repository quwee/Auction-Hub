package com.example.auctionservice.service;

import com.example.shared.dto.chat.ChatMessageRequestDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;

public interface ChatMessageService {

    ChatMessageResponseDto createChatMessage(ChatMessageRequestDto chatMessageRequestDto);

}
