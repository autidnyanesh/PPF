package com.idbi.ppf.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Verifies JWTs issued by GBM. PPF never creates or refreshes tokens.
 * Keep claim names in sync with GBM: sub, uid, role, displayName, jti.
 */
@Service
public class JwtService {
    private final SecretKey key;

    public JwtService(@Value("${gbm.jwt.secret}") String secret) {
        byte[] bytes = secret.getBytes(StandardCharsets.UTF_8);
        if (bytes.length < 32) {
            throw new IllegalStateException("gbm.jwt.secret must be at least 32 bytes and match GBM");
        }
        this.key = Keys.hmacShaKeyFor(bytes);
    }

    public Claims parse(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public static String extractBearer(String authorizationHeader) {
        if (authorizationHeader == null) {
            return null;
        }
        String value = authorizationHeader.trim();
        if (value.length() < 8 || !value.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return null;
        }
        String token = value.substring(7).trim();
        return token.isEmpty() ? null : token;
    }
}
