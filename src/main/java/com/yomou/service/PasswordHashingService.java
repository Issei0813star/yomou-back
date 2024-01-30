package com.yomou.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordHashingService {

    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordHashingService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String encodePassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }
}

