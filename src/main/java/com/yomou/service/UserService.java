package com.yomou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yomou.repository.UserRepository;
import com.yomou.dto.UserRequest;
import com.yomou.entity.UserEntity;
import com.yomou.util.PasswordHashingUtil;
import com.yomou.util.JwtGenerator;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordHashingUtil passwordHashingUtil;
    private final JwtGenerator jwtGenerator;

    public ResponseEntity<Object> createUser(UserRequest dto) {
        if(checkIsEmailUnique(dto.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error_message", dto.getEmail()+ "はすでに登録されています"));
        UserEntity user = new UserEntity();
        user.setUserName(dto.getUserName());
        user.setPassword(passwordHashingUtil.encodePassword(dto.getPassword()));
        user.setEmail(dto.getEmail());


        UserEntity createdUser = userRepository.save(user);
        String token = jwtGenerator.generateToken(createdUser);
        return ResponseEntity.ok().body(Map.of("token", token));
    }

    private boolean checkIsEmailUnique(String email){
        return (boolean)userRepository.existsByEmail(email);
    }


}
