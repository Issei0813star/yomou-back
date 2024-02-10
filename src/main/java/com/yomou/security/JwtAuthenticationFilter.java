package com.yomou.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Objects;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${spring.jwt.secret-key}")
    private String SECRET_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (isExcludedPath(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request.getHeader("Authorization"));
        try {
            Claims claims = getClaims(token);

            if (Objects.nonNull(token) && Objects.nonNull(claims)) {
                Authentication authentication = createAuthentication(claims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
            else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private Claims getClaims(String token) {
        try{
            byte[] secretKeyBytes = SECRET_KEY.getBytes();
            SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return jws.getBody();
        }
        catch(Exception e){
            return null;
        }
    }

    private boolean isExcludedPath(String path) {
        return path.equals("/auth/login") || path.equals("/user/create");
    }

    private String extractToken (String bearer){
        if(Objects.nonNull(bearer) && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }

    private Authentication createAuthentication(Claims claims) {
       String userName = (String) claims.get("user_name");
       UserDetails user = new CustomUser(userName, "email");
        return new UsernamePasswordAuthenticationToken(user, null, null);
    }
}
