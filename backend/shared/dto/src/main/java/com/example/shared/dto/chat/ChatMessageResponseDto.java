package com.example.shared.dto.chat;

import jakarta.validation.constraints.NotBlank;
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
public class ChatMessageResponseDto implements Serializable {

    @NotNull
    @Positive
    private Long auctionId;

    @NotNull
    @Positive
    private Long senderId;

    private String firstName;

    private String lastName;

    @NotBlank
    private String content;

    private LocalDateTime sentAt;
}
