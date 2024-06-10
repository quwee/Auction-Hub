package com.example.auctionservice.model.dto;

import com.example.shared.dto.constants.PatternConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionCreateRequestDto {

    @NotBlank(message = "Поле не должно быть пустым")
    private String lotName;

    @NotNull(message = "Поле не должно быть пустым")
    @Positive(message = "Поле должно быть положительным целым числом")
    private Integer minPrice;

    @NotNull(message = "Поле не должно быть пустым")
    @Positive(message = "Поле должно быть положительным целым числом")
    private Integer priceStep;

    @NotNull(message = "Поле не должно быть пустым")
    @Future(message = "Должно быть датой в будущем")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = PatternConstants.DATE_TIME_PATTERN)
    private LocalDateTime endDate;

    @NotBlank(message = "Поле не должно быть пустым")
    private String lotDesc;

}
