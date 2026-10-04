package com.example.practice4.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateArtworkRequest(
        @NotBlank @Size(max = 200) String title,
        Integer createdYear,
        @PositiveOrZero BigDecimal price,
        @Size(max = 60) String style,
        @NotNull Long artistId) { }
