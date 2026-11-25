package com.example.practice7.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class JwtService {

    private final JwtEncoder encoder;
    private final long ttlSeconds;

    public JwtService(JwtEncoder encoder,
                      @Value("${app.security.jwt.ttl-seconds:3600}") long ttlSeconds) {
        this.encoder = encoder;
        this.ttlSeconds = ttlSeconds;
    }

    public long ttlSeconds() { return ttlSeconds; }

    public String issue(Authentication authentication) {
        Instant now = Instant.now();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)          // "ROLE_ADMIN"
                .toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("gallery-api")
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(ttlSeconds))
                .claim("roles", roles)
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();   // подпись HMAC-SHA256
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}