package com.example.practice7.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(example = "admin") @NotBlank String username,
        @Schema(example = "admin12345") @NotBlank String password) { }
