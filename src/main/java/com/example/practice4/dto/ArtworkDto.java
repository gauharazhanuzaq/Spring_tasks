package com.example.practice4.dto;

import java.math.BigDecimal;

public record ArtworkDto(Long id, String title, Integer createdYear, BigDecimal price,
                         String style, Long artistId, String artistName) { }
