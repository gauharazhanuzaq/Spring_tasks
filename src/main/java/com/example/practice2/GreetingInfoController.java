package com.example.practice2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingInfoController {

    private final GreetingProperties properties;

    public GreetingInfoController(GreetingProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/greeting-info")
    public GreetingProperties greetingInfo() {
        return properties;
    }
}