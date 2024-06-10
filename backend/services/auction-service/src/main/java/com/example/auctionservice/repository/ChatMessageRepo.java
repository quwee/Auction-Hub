package com.example.auctionservice.repository;

import com.example.auctionservice.model.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByAuctionId(Long auctionId);

}
