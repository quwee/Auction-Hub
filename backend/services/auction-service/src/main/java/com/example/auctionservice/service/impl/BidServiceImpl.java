package com.example.auctionservice.service.impl;

import com.example.auctionservice.exception.AuctionAlreadyCompletedException;
import com.example.auctionservice.exception.BidNotFoundException;
import com.example.auctionservice.exception.BidRaiseException;
import com.example.auctionservice.model.entity.Auction;
import com.example.auctionservice.model.entity.Bid;
import com.example.auctionservice.model.enums.AuctionStatus;
import com.example.auctionservice.model.mapper.BidMapper;
import com.example.auctionservice.repository.BidRepo;
import com.example.auctionservice.service.AuctionService;
import com.example.auctionservice.service.BidService;
import com.example.auctionservice.service.util.AuctionServiceUtils;
import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.bid.BidResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BidServiceImpl implements BidService {

    private final BidRepo bidRepo;
    private final AuctionService auctionService;
    private final AuctionServiceUtils auctionServiceUtils;
    private final BidMapper bidMapper;

    @Override
    @Transactional
    public BidResponseDto createBid(BidRequestDto bidRequestDto) {
        Auction auction = auctionService.getAuctionById(bidRequestDto.getAuctionId());

        if (auction.getStatus().equals(AuctionStatus.COMPLETED)) {
            throw new AuctionAlreadyCompletedException(
                    "Auction with id %d is already completed".formatted(auction.getId()));
        }

        var bids = auction.getBids().stream()
                .filter(bid -> Objects.equals(bid.getUserId(), bidRequestDto.getUserId()))
                .toList();
        Bid bid;
        if (bids.isEmpty()) {
            bid = bidMapper.bidRequestDtoToBid(bidRequestDto, LocalDateTime.now(), auction);
        } else {
            bid = bids.get(0);
            bid.setPrice(bidRequestDto.getPrice());
            bid.setPlacedAt(LocalDateTime.now());
        }
        bid = bidRepo.save(bid);
        return bidMapper.bidToBidResponseDto(bid);
    }

    @Override
    @Transactional
    public BidResponseDto raiseBid(BidRequestDto bidRequestDto) {
        Bid bid = bidRepo.findById(bidRequestDto.getId()).orElseThrow(() ->
                new BidNotFoundException("Bid with id %d not found".formatted(bidRequestDto.getId())));

        if (bid.getAuction().getStatus().equals(AuctionStatus.COMPLETED)) {
            throw new AuctionAlreadyCompletedException(
                    "Auction with id %d is already completed".formatted(bid.getAuction().getId()));
        }

        Integer maxPrice = auctionServiceUtils.getMaxPriceBid(bid.getAuction().getBids());

        if (bidRequestDto.getPrice() <= maxPrice) {
            throw new BidRaiseException("Bid price less than current price");
        }

        bid.setPrice(bidRequestDto.getPrice());
        bid.setPlacedAt(LocalDateTime.now());

        bidRepo.save(bid);

        return bidMapper.bidToBidResponseDto(bid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BidResponseDto> getBids(Long auctionId) {
        return bidRepo.findAllByAuctionId(auctionId)
                .stream().map(bidMapper::bidToBidResponseDto)
                .toList();
    }

}
