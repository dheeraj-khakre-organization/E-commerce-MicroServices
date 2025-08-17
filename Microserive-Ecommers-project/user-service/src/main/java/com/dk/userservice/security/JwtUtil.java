package com.dk.userservice.security;

import com.dk.userservice.exception.JwtValidationException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Generate a secure key once and store the Base64 string in application.properties
    // Example: jwt.secret=Base64EncodedKeyFromKeys.secretKeyFor(SignatureAlgorithm.HS512)
    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long expiration;

    public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
        // Decode the Base64 key from config
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(base64Secret));
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public void validateTokenOrThrow(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new JwtValidationException("Token expired");
        } catch (UnsupportedJwtException e) {
            throw new JwtValidationException("Unsupported token");
        } catch (MalformedJwtException e) {
            throw new JwtValidationException("Malformed token");
        } catch (SecurityException e) {
            throw new JwtValidationException("Invalid signature");
        } catch (IllegalArgumentException e) {
            throw new JwtValidationException("Token is null or empty");
        }
    }

    // Helper to generate a secure key (run once and store in properties)
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        System.out.println("Base64 Secret: " + Encoders.BASE64.encode(key.getEncoded()));
    }
}
