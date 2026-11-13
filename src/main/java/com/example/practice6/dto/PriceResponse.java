package com.example.practice6.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record PriceResponse(
        Long artworkId,
        String title,
        BigDecimal price,
        String baseCurrency,
        String targetCurrency,
        @Schema(description = "Price in the target currency; null when fallback is used")
        BigDecimal convertedPrice,
        @Schema(description = "Exchange rate used; null when fallback is used")
        BigDecimal rate,
        @Schema(description = "true when the external service failed and the base price is returned")
        boolean fallback,
        String message) { }