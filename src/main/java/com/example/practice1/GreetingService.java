package com.example.practice1;

import com.example.practice3.GreetingProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GreetingService {

    private final Optional<GreetingProvider> greetingProvider;

    public GreetingService(Optional<GreetingProvider> greetingProvider) {
        this.greetingProvider = greetingProvider;
    }

    public String getGreeting() {
        return greetingProvider
                .map(GreetingProvider::provide)
                .orElse("Greeting is currently disabled");
    }
}