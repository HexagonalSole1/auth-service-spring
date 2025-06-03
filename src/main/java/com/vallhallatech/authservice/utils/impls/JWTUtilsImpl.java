// ===== VERSIÓN MEJORADA DE JWTUtilsImpl (AUTH-SERVICE) =====
package com.vallhallatech.authservice.utils.impls;

import com.vallhallatech.authservice.types.JWTType;
import com.vallhallatech.authservice.utils.IJWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtilsImpl implements IJWTUtils {
    @Value("${jwt.access-token-secret-key}")
    private String ACCESS_TOKEN_SECRET_KEY;

    @Value("${jwt.access-token-expiration-in-ms}")
    private Long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh-token-secret-key}")
    private String REFRESH_TOKEN_SECRET_KEY;

    @Value("${jwt.refresh-token-expiration-in-ms}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;

    @PostConstruct
    public void logJwtConfig() {
        System.out.println("🔑 [AUTH-SERVICE] JWT Config Loaded:");
        System.out.println("🔑 [AUTH-SERVICE] Secret Key: " + ACCESS_TOKEN_SECRET_KEY.substring(0, Math.min(20, ACCESS_TOKEN_SECRET_KEY.length())) + "...");
        System.out.println("🔑 [AUTH-SERVICE] Secret Length: " + ACCESS_TOKEN_SECRET_KEY.length());
        System.out.println("🔑 [AUTH-SERVICE] Secret Hash: " + ACCESS_TOKEN_SECRET_KEY.hashCode());
        System.out.println("🔑 [AUTH-SERVICE] Expiration: " + ACCESS_TOKEN_EXPIRATION_TIME + "ms");
    }

    @Override
    public String generateToken(String email, Map<String, Object> payload, JWTType tokenType) {
        String secretKey = getTokenSecretKey(tokenType);
        Date expirationTime = getTokenExpirationTime(tokenType);

        System.out.println("🟡 [AUTH-SERVICE] Generating token for: " + email);
        System.out.println("🟡 [AUTH-SERVICE] Payload: " + payload);
        System.out.println("🟡 [AUTH-SERVICE] Using secret: " + secretKey.substring(0, Math.min(20, secretKey.length())) + "...");

        String token = Jwts.builder()
                .subject(email)
                .claims(payload)
                .expiration(expirationTime)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();

        System.out.println("🟢 [AUTH-SERVICE] Token generated: " + token.substring(0, Math.min(50, token.length())) + "...");

        // VERIFICAR QUE EL TOKEN SE PUEDE LEER CON LA MISMA CLAVE
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            System.out.println("✅ [AUTH-SERVICE] Token verification successful:");
            System.out.println("✅ [AUTH-SERVICE] Subject: " + claims.getSubject());
            System.out.println("✅ [AUTH-SERVICE] Claims: " + claims);
        } catch (Exception e) {
            System.err.println("❌ [AUTH-SERVICE] Token verification failed: " + e.getMessage());
        }

        return token;
    }

    @Override
    public String getSubjectFromToken(String token, JWTType tokenType) {
        String secretKey = getTokenSecretKey(tokenType);

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload().getSubject();
    }

    @Override
    public Map<String, Object> getClaimsFromToken(String token, JWTType tokenType) {
        String secretKey = getTokenSecretKey(tokenType);

        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Boolean isTokenValid(String token, JWTType tokenType) {
        String secretKey = getTokenSecretKey(tokenType);

        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String getTokenSecretKey(JWTType type) {
        return switch (type) {
            case ACCESS_TOKEN -> ACCESS_TOKEN_SECRET_KEY;
            case REFRESH_TOKEN -> REFRESH_TOKEN_SECRET_KEY;
        };
    }

    private Date getTokenExpirationTime(JWTType type) {
        return switch (type) {
            case ACCESS_TOKEN -> new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME);
            case REFRESH_TOKEN -> new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME);
        };
    }
}