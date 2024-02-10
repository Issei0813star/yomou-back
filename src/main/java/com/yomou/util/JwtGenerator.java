package com.yomou.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtGenerator {
    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;


    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 864000000);

        byte[] secretKeyBytes = SECRET_KEY.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

        return Jwts.builder()
                .setExpiration(expirationDate)
                .claim("user_name", userDetails.getUsername())
                .signWith(key)
                .compact();
    }
}


