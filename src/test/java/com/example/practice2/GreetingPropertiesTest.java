package com.example.practice2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class GreetingPropertiesTest {

    @Autowired
    private GreetingProperties properties;

    @Test
    void bindsTestProfileValues() {
        assertThat(properties.message()).isEqualTo("Hello from TEST");
        assertThat(properties.maxLength()).isEqualTo(50);
        assertThat(properties.enabled()).isTrue();
    }
}