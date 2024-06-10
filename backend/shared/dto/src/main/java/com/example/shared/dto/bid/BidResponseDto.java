package com.example.shared.dto.bid;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidResponseDto implements Serializable {

    @Positive
    private Long id;

    @NotNull
    @Positive
    private Long auctionId;

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Integer price;

    private LocalDateTime placedAt;

}
