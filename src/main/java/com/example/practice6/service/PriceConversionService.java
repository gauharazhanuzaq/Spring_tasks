package com.example.practice6.service;

import com.example.practice4.dto.ArtworkDto;
import com.example.practice4.service.ArtworkService;
import com.example.practice6.client.ExchangeRateClient;
import com.example.practice6.dto.PriceResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PriceConversionService {

    private static final String BASE = "USD"; 

    private final ArtworkService artworkService;
    private final ExchangeRateClient rateClient;

    public PriceConversionService(ArtworkService artworkService, ExchangeRateClient rateClient) {
        this.artworkService = artworkService;
        this.rateClient = rateClient;
    }


    public PriceResponse convert(Long artworkId, String currency) {
        String target = currency == null ? "" : currency.trim().toUpperCase();
        if (!target.matches("[A-Z]{3}")) {
            throw new IllegalArgumentException("currency must be a 3-letter ISO code, e.g. KZT");
        }

        ArtworkDto artwork = artworkService.get(artworkId);   // 404, если картины нет
        BigDecimal price = artwork.price();

        if (price == null) {
            return new PriceResponse(artwork.id(), artwork.title(), null, BASE, target,
                    null, null, false, "Artwork has no price");
        }
        if (BASE.equals(target)) {
            return new PriceResponse(artwork.id(), artwork.title(), price, BASE, target,
                    price, BigDecimal.ONE, false, "Base currency, no conversion needed");
        }

        return rateClient.getRate(BASE, target)
                .map(rate -> new PriceResponse(artwork.id(), artwork.title(), price, BASE, target,
                        price.multiply(rate).setScale(2, RoundingMode.HALF_UP), rate,
                        false, "Converted at the current rate"))
                .orElseGet(() -> new PriceResponse(artwork.id(), artwork.title(), price, BASE, target,
                        null, null, true,
                        "Exchange rate service unavailable, showing price in " + BASE));   // fallback
    }
}