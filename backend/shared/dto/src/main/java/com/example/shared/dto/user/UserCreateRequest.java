package com.example.shared.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequest {

    @NotNull(message = "Поле не должно быть пустым")
    @Positive(message = "Поле должно быть положительным целым числом")
    private Long id;

    @NotNull(message = "Поле не должно быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Поле не должно быть пустым")
    private String firstName;

    @NotBlank(message = "Поле не должно быть пустым")
    private String lastName;

}
