package com.example.auctionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionSimpDto {
    private Long id;
    private String lotName;
    private Integer currentPrice;
    private LocalDateTime endDate;
    private String imagePath;
}
