package com.codemystack.apps.supagym.security;

import com.codemystack.apps.supagym.api.auth.dto.JwtUserView;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {
    private final Key signingKey;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long expirationMs
    ) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationMs = expirationMs;
    }

    public String generateToken(
            UUID userId,
            String email,
            String role,
            UUID tenantId,
            String tenantSchema
    ) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(userId.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs)))
                .claims(Map.of(
                        "email", email,
                        "role", role,
                        "tenantId", tenantId,
                        "tenantSchema", tenantSchema
                ))
                .signWith(signingKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public JwtUserView toUserView(Claims claims) {
        UUID userId = UUID.fromString(claims.getSubject());
        String email = claims.get("email", String.class);
        String role = claims.get("role", String.class);
        String fullName = claims.get("fullName", String.class);
        return new  JwtUserView(userId, email, fullName, role);
    }
}
