package com.example.practice7.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Schema(example = "anna") @NotBlank @Size(min = 3, max = 50) String username,
        @Schema(example = "secret12345") @NotBlank @Size(min = 8, max = 72) String password) { }
