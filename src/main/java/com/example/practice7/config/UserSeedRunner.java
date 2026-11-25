package com.example.practice7.config;

import com.example.practice7.entity.AppUser;
import com.example.practice7.repository.AppUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class UserSeedRunner implements CommandLineRunner {

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;

    public UserSeedRunner(AppUserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createIfMissing("admin", "admin12345", "ADMIN");
        createIfMissing("user", "user12345", "USER");
    }

    private void createIfMissing(String username, String rawPassword, String role) {
        if (!users.existsByUsername(username)) {
            users.save(new AppUser(username, passwordEncoder.encode(rawPassword), role));
        }
    }
}