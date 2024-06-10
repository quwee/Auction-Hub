package com.example.shared.dto.auction;

import com.example.shared.dto.bid.BidResponseDto;
import com.example.shared.dto.chat.ChatMessageResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionDetailsDto {

    private AuctionDto auction;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AuctionCompleteDto auctionComplete;

    private List<BidResponseDto> bids;

    private List<ChatMessageResponseDto> chatMessages;
}
