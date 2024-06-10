package com.example.shared.dto.auction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionDto {
    private String lotName;
    private String lotDesc;
    private Long ownerId;
    private String ownerFullName;
    private Integer currentPrice;
    private Integer priceStep;
    private LocalDateTime endDate;
    private List<String> imgNames;
}
