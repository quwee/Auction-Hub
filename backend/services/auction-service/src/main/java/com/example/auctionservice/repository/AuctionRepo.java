package com.example.auctionservice.repository;

import com.example.auctionservice.model.entity.Auction;
import com.example.auctionservice.model.enums.AuctionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepo extends JpaRepository<Auction, Long> {

    List<Auction> findAllByStatus(AuctionStatus status);

}
