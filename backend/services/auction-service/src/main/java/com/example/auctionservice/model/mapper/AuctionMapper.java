package com.example.auctionservice.model.mapper;

import com.example.auctionservice.model.dto.AuctionCreateRequestDto;
import com.example.auctionservice.model.dto.AuctionSimpDto;
import com.example.auctionservice.model.entity.Auction;
import com.example.auctionservice.model.enums.AuctionStatus;
import com.example.shared.dto.auction.AuctionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AuctionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bids", ignore = true)
    @Mapping(target = "chatMessages", ignore = true)
    Auction auctionCreateRequestDtoToAuction(
            AuctionCreateRequestDto auctionRequest,
            Long ownerId,
            LocalDateTime startDate,
            AuctionStatus status,
            List<String> imgNames
    );

    AuctionSimpDto auctionToAuctionSimpDto(Auction auction, Integer currentPrice, String imagePath);

    AuctionDto auctionToAuctionDto(Auction auction, String ownerFullName, Integer currentPrice);
}
