package com.example.practice2;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app.greeting")
public record GreetingProperties(
        @NotBlank String message,
        @Positive int maxLength,
        boolean enabled
) {
}