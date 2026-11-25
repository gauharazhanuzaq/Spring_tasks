package com.example.practice7.dto;

public record TokenResponse(String accessToken, String tokenType, long expiresInSeconds) { }
