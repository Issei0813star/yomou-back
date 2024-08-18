package com.yomou.util;

import com.yomou.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtGenerator {
    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;


    public String generateToken(User user) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 864000000);

        byte[] secretKeyBytes = SECRET_KEY.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

        return Jwts.builder()
                .setExpiration(expirationDate)
                .claim("user_name", user.getUserName())
                .claim("email", user.getEmail())
                .signWith(key)
                .compact();
    }
}


