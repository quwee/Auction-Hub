package com.example.shared.dto.bid;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BidRequestDto implements Serializable {

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

}
