package com.example.practice7;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordEncodingTest {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void hashIsNotThePlainPassword() {
        String hash = encoder.encode("secret12345");
        assertThat(hash).isNotEqualTo("secret12345");
        assertThat(hash).startsWith("$2");
    }

    @Test
    void matchesCorrectPasswordOnly() {
        String hash = encoder.encode("secret12345");
        assertThat(encoder.matches("secret12345", hash)).isTrue();
        assertThat(encoder.matches("wrong-password", hash)).isFalse();
    }

    @Test
    void samePasswordGivesDifferentHashes() {
        assertThat(encoder.encode("secret12345")).isNotEqualTo(encoder.encode("secret12345"));
    }
}