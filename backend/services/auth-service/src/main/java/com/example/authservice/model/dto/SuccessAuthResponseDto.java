package com.example.authservice.model.dto;

import com.example.shared.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuccessAuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private UserDto user;
}
