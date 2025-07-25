package com.instaprepsai.auth.security.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    private static final String SECRET = "cEz4bjlKQ612Jhx0QXLI5ocB3sLspQBODpmvaV6oCln3wkgTGVwtbLgvF3h6K4lA"; // Replace with a secure key
    private static final String ALGORITHM = "HmacSHA256";

    private final SecretKey signingKey = new SecretKeySpec(SECRET.getBytes(), ALGORITHM);

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(signingKey)
                .compact();
    }

    public String generateTokenForRefreshToken(String refreshToken) {
        return generateToken(extractUsername(refreshToken));
    }

    public Map<?, ?> extractPayload(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Boolean validateToken(String token, String username) {
        if (isValidJwtToken(token) && !isTokenExpired(token)) {
            return username.equals(extractUsername(token));
        }
        return false;
    }

    public boolean isValidJwtToken(String token) {
        try {
            if (!extractPayload(token).isEmpty())
                return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
