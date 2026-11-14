package com.example.practice6.controller;

import com.example.practice6.dto.PriceResponse;
import com.example.practice6.service.PriceConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/artworks")
@Tag(name = "Artwork prices", description = "Price conversion via an external exchange-rate service")
public class ArtworkPriceController {

    private final PriceConversionService service;

    public ArtworkPriceController(PriceConversionService service) {
        this.service = service;
    }

    @Operation(summary = "Artwork price in another currency",
               description = "Calls an external exchange-rate API (connect timeout 1 s, read timeout 2 s). "
                           + "If it fails, returns the price in USD with fallback=true.")
    @ApiResponse(responseCode = "200", description = "Converted price, or fallback with the base price")
    @ApiResponse(responseCode = "400", description = "Invalid currency code")
    @ApiResponse(responseCode = "404", description = "Artwork not found")
    @ApiResponse(responseCode = "404", description = "Artwork not found",
        content = @Content(mediaType = "application/problem+json",
                schema = @Schema(implementation = ProblemDetail.class)))
    @GetMapping("/{id}/price")
    public PriceResponse price(
            @PathVariable Long id,
            @Parameter(description = "3-letter ISO currency code, e.g. KZT, EUR") 
            @RequestParam(defaultValue = "EUR") String currency) {
        return service.convert(id, currency);
    }
}