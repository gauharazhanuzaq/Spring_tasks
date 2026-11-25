package com.example.practice7.controller;

import com.example.practice7.dto.UserDto;
import com.example.practice7.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Protected", description = "Endpoints that require a token")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final AuthService authService;

    public AdminController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "List all users (ADMIN only)")
    @ApiResponse(responseCode = "200", description = "Users")
    @ApiResponse(responseCode = "401", description = "Missing or invalid token")
    @ApiResponse(responseCode = "403", description = "Authenticated, but not an ADMIN")
    @GetMapping("/admin/users")
    public List<UserDto> users() {
        return authService.listUsers();
    }

    @Operation(summary = "Current user from the token (any authenticated user)")
    @ApiResponse(responseCode = "401", description = "Missing or invalid token")
    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal Jwt jwt) {
        return Map.of("username", jwt.getSubject(), "roles", jwt.getClaimAsStringList("roles"));
    }
}