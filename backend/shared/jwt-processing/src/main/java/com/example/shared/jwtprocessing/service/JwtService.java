package com.example.shared.jwtprocessing.service;

import com.example.shared.jwtprocessing.model.User;
import com.example.shared.jwtprocessing.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.access-millis}")
    private long accessMillis;

    @Value("${app.jwt.refresh-millis}")
    private long refreshMillis;

    public String createAccessToken(Long userId, String email, String role) {
        log.info("Creating access token for user with id: {}", userId);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + accessMillis);

        log.debug("createAccessToken: accessMillis: {}", accessMillis);
        log.debug("createAccessToken: now: {}", now);
        log.debug("createAccessToken: expirationDate: {}", expirationDate);

        return Jwts.builder()
                .subject(email)
                .claim(SecurityConstants.USER_ID_CLAIM, userId)
                .claim(SecurityConstants.USER_EMAIL_CLAIM, email)
                .claim(SecurityConstants.USER_ROLE_CLAIM, role)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getKey())
                .compact();
    }

    public String createRefreshToken(Long userId, String email, String role) {
        log.info("Creating refresh token for user with id: {}", userId);

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + refreshMillis);

        log.debug("createRefreshToken: refreshMillis: {}", refreshMillis);
        log.debug("createRefreshToken: now: {}", now);
        log.debug("createRefreshToken: expirationDate: {}", expirationDate);

        return Jwts.builder()
                .subject(email)
                .claim(SecurityConstants.USER_ID_CLAIM, userId)
                .claim(SecurityConstants.USER_EMAIL_CLAIM, email)
                .claim(SecurityConstants.USER_ROLE_CLAIM, role)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getKey())
                .compact();
    }

    public void validateToken(String token) {
        log.debug("validateToken: start");

        Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token);

        log.debug("validateToken: end");
    }

    public User extractUser(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return new User(
                claims.get(SecurityConstants.USER_ID_CLAIM, Long.class),
                claims.get(SecurityConstants.USER_EMAIL_CLAIM, String.class),
                claims.get(SecurityConstants.USER_ROLE_CLAIM, String.class));
    }

    public String extractRole(String token) {
        return extractClaim(token, SecurityConstants.USER_ROLE_CLAIM, String.class);
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    private <T> T extractClaim(String token, String claimName, Class<T> type) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(claimName, type);
    }
}
