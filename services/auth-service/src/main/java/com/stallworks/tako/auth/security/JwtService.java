package com.stallworks.tako.auth.security;

import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stallworks.tako.auth.entity.Account;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey key;
    private final long expirationMs;
    
    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms:3600000}") long expirationMs) {

        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 64) {
            throw new IllegalArgumentException("JWT secret must be at least 64 characters long (512 bits) for HS512");
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMs = expirationMs;
    }
    
    public String generateToken(Account account) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(account.getUserName())
                .claim("employeeId", account.getEmployeeId())
                .claim("role", account.getRole().name())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key, Jwts.SIG.HS512)   // Explicit algorithm
                .compact();
    }
    
    // For service-to-service calls (e.g. EmployeeClient → core-service),
    // not tied to any real Account — a synthetic identity representing
    // "auth-service itself." Short-lived on purpose: minted fresh for each
    // call rather than cached, since it's cheap to generate and there's no
    // benefit to a service token outliving the single request it's used for.
    public String generateServiceToken() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 60_000); // 1 minute

        return Jwts.builder()
                .subject("auth-service")
                .claim("role", "SERVICE")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key, Jwts.SIG.HS512)
                .compact();
    }

    public Claims parseToken(String token) {
	    return Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload();
	}
    
}
