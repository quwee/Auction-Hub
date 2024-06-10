package com.example.shared.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRegisterEvent implements Serializable {
    private String email;
    private String fullName;
    private String token;
}
