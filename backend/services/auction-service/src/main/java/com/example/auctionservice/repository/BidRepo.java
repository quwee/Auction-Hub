package com.example.auctionservice.repository;

import com.example.auctionservice.model.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepo extends JpaRepository<Bid, Long> {

    List<Bid> findAllByAuctionId(Long id);

}
