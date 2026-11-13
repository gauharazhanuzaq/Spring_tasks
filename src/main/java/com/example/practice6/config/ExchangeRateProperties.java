package com.example.practice6.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.time.Duration;

@ConfigurationProperties(prefix = "app.rates")
public record ExchangeRateProperties(String baseUrl, Duration connectTimeout, Duration readTimeout) { }