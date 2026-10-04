package com.example.practice4.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateArtistRequest(
        @NotBlank @Size(max = 120) String name,
        @Size(max = 60) String country,
        @Min(0) @Max(2100) Integer birthYear) { } 