package com.example.practice7.service;

import com.example.practice7.dto.*;
import com.example.practice7.entity.AppUser;
import com.example.practice7.exception.ConflictException;
import com.example.practice7.repository.AppUserRepository;
import com.example.practice7.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthService {

    private final AppUserRepository users;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AppUserRepository users, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtService jwtService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Transactional
    public UserDto register(RegisterRequest request) {
        String username = request.username().trim();
        if (users.existsByUsername(username)) {
            throw new ConflictException("Username already taken");
        }
        AppUser saved = users.save(new AppUser(
                username, passwordEncoder.encode(request.password()), "USER"));   // роль всегда USER
        return new UserDto(saved.getId(), saved.getUsername(), saved.getRole());
    }

    public TokenResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(      // сверяет пароль с хешем
                new UsernamePasswordAuthenticationToken(request.username().trim(), request.password()));
        return new TokenResponse(jwtService.issue(auth), "Bearer", jwtService.ttlSeconds());
    }

    @Transactional(readOnly = true)
    public List<UserDto> listUsers() {
        return users.findAll().stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getRole()))
                .toList();
    }
}