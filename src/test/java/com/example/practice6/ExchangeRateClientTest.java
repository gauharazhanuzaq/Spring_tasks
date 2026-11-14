package com.example.practice6;

import com.example.practice6.client.ExchangeRateClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class ExchangeRateClientTest {

    private MockRestServiceServer server;
    private ExchangeRateClient client;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder().baseUrl("https://rates.test");
        server = MockRestServiceServer.bindTo(builder).build();
        client = new ExchangeRateClient(builder.build());
    }

    @Test
    void returnsRateWhenServiceWorks() {
        server.expect(requestTo("https://rates.test/latest/USD"))
              .andRespond(withSuccess("{\"result\":\"success\",\"rates\":{\"KZT\":500}}",
                                      MediaType.APPLICATION_JSON));

        assertThat(client.getRate("USD", "KZT")).contains(new BigDecimal("500"));
    }

    @Test
    void returnsEmptyWhenServiceReturns500() {
        server.expect(requestTo("https://rates.test/latest/USD"))
              .andRespond(withServerError());

        assertThat(client.getRate("USD", "KZT")).isEmpty();      // fallback-сигнал
    }

    @Test
    void returnsEmptyOnTimeout() {
        server.expect(requestTo("https://rates.test/latest/USD"))
              .andRespond(withException(new IOException("timeout")));

        assertThat(client.getRate("USD", "KZT")).isEmpty();
    }
}