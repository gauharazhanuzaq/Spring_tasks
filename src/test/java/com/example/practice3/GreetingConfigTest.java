package com.example.practice3;

import com.example.practice1.GreetingService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class GreetingConfigTest {

    @Autowired
    private GreetingService greetingService;

    @Test
    void returnsConfiguredMessageWhenEnabled() {
        // app.greeting.enabled=true в application-test.properties
        assertThat(greetingService.getGreeting()).isEqualTo("Hello from TEST");
    }

    @Nested
    @TestPropertySource(properties = "app.greeting.enabled=false")
    class WhenDisabled {

        @Autowired
        private GreetingService greetingService;

        @Test
        void fallsBackWhenBeanMissing() {
            assertThat(greetingService.getGreeting())
                    .isEqualTo("Greeting is currently disabled");
        }
    }
}