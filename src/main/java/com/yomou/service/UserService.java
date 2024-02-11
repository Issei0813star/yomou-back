package com.yomou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yomou.repository.UserRepository;
import com.yomou.dto.UserDto;
import com.yomou.entity.UserEntity;
import com.yomou.util.PasswordHashingUtil;
import com.yomou.util.JwtGenerator;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordHashingUtil passwordHashingUtil;
    private final JwtGenerator jwtGenerator;

    public ResponseEntity<Object> createUser(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setUserName(dto.getUserName());
        user.setPassword(passwordHashingUtil.encodePassword(dto.getPassword()));
        user.setEmail(dto.getEmail());


        UserEntity createdUser = userRepository.save(user);
        String token = jwtGenerator.generateToken(createdUser);
        return ResponseEntity.ok().body(Map.of("token", token));
    }

}
