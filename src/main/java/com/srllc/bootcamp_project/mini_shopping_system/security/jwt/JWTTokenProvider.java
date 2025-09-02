package com.srllc.bootcamp_project.mini_shopping_system.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;


// Generating of bearer token
@Data
@Component
@ConfigurationProperties(prefix = "app")
public class JWTTokenProvider {

    private String jwtSecret;

    private long jwtExpirationMilliseconds;

    // Generate JWT Token
    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);

        return Jwts.builder()
                .subject(username)
                .issuedAt(currentDate)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    // Convert and encode secret key from BASE64
    // Step 1: Take jwtSecret
    // Step 2: Decode
    // Step 3: Use hmacShaKeyFor to generate securedKey
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Extract username from bearer token
    // Step 1: Parse JWT and verifies its signature using the signing key
    // Step 2: Extracts the payload (claims) from the token
    // Step 3: Gets the subject (sub) field, which is username
    public String getUserName(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();

    }

    // Validating the token
    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (IllegalArgumentException | JwtException e) {
            return false;
        }
    }
}
