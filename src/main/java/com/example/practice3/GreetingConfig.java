package com.example.practice3;

import com.example.practice2.GreetingProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingConfig {

    @Bean
    @ConditionalOnProperty(prefix = "app.greeting", name = "enabled", havingValue = "true")
    public GreetingProvider greetingProvider(GreetingProperties properties) {
        return properties::message;
    }
}