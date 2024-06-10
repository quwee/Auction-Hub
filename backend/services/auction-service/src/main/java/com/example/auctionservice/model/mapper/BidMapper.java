package com.example.auctionservice.model.mapper;

import com.example.auctionservice.model.entity.Auction;
import com.example.auctionservice.model.entity.Bid;
import com.example.shared.dto.bid.BidRequestDto;
import com.example.shared.dto.bid.BidResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface BidMapper {

    @Mapping(target = "auctionId", source = "auction.id")
    BidResponseDto bidToBidResponseDto(Bid bid);

    @Mapping(target = "id", source = "bidRequestDto.id")
    Bid bidRequestDtoToBid(BidRequestDto bidRequestDto, LocalDateTime placedAt, Auction auction);

}
