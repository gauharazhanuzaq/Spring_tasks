package com.example.practice1;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

    public String getGreeting() {
        return "Hello, Gauhar!";
    }
}