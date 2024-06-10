package com.example.shared.dto.chat;

import jakarta.validation.constraints.NotBlank;
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
public class ChatMessageRequestDto implements Serializable {

    @NotNull
    @Positive
    private Long auctionId;

    @NotNull
    @Positive
    private Long senderId;

    @NotBlank
    private String content;

}
