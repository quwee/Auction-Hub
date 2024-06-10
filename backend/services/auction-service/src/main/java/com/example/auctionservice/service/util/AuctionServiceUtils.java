package com.example.auctionservice.service.util;

import com.example.auctionservice.model.entity.Bid;
import com.example.shared.dto.bid.BidResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionServiceUtils {

    public Integer getMaxPriceBidDto(List<BidResponseDto> bids, Integer minPrice) {
        return bids.stream()
                .map(BidResponseDto::getPrice)
                .max(Integer::compareTo)
                .orElse(minPrice);
    }

    public Integer getMaxPriceBid(List<Bid> bids) {
        return bids.stream()
                .map(Bid::getPrice)
                .max(Integer::compareTo)
                .orElseThrow();
    }
}
