package com.example.practice6.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class ExchangeRateClient {

    private static final Logger log = LoggerFactory.getLogger(ExchangeRateClient.class);

    private final RestClient restClient;

    public ExchangeRateClient(@Qualifier("exchangeRateRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public Optional<BigDecimal> getRate(String base, String target) {
        try {
            ExchangeRateApiResponse body = restClient.get()
                    .uri("/latest/{base}", base)
                    .retrieve()
                    .body(ExchangeRateApiResponse.class);

            if (body == null || body.rates() == null || !body.rates().containsKey(target)) {
                log.warn("Exchange rate {}->{} not found in response", base, target);
                return Optional.empty();
            }
            return Optional.of(body.rates().get(target));

        } catch (RestClientException ex) {   // таймаут, отказ соединения, 4xx/5xx
            log.warn("Exchange rate service failed ({}->{}): {}", base, target, ex.toString());
            return Optional.empty();
        }
    }
}