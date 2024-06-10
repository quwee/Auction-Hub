package com.example.authservice.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterRequestDto {

    @NotBlank(message = "Поле не должно быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Поле не должно быть пустым")
    private String firstName;

    @NotBlank(message = "Поле не должно быть пустым")
    private String lastName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Пароль должен состоять как минимум из 1 цифры, 1 заглавной буквы, "
                    + "1 прописной буквы и специального символа"
    )
    @NotBlank(message = "Пароль не должен быть пустым")
    @Length(min = 8, max = 40, message = "Длина пароля должна быть от 8 до 40 символов")
    private String password;

    @NotNull(message = "Подтвержденный пароль не должен быть пустым")
    private String passwordConfirmation;
}
