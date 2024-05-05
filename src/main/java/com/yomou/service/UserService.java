package com.yomou.service;

import com.yomou.exception.YomouException;
import com.yomou.exception.YomouMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yomou.repository.UserRepository;
import com.yomou.dto.UserRegistrationRequestDto;
import com.yomou.entity.UserEntity;
import com.yomou.util.PasswordHashingUtil;
import com.yomou.util.JwtGenerator;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordHashingUtil passwordHashingUtil;
    private final JwtGenerator jwtGenerator;

    public Map<String, Object> createUser(UserRegistrationRequestDto dto) {
        checkIsUserUnique(dto);
        UserEntity user = new UserEntity();
        user.setUserName(dto.getUserName());
        user.setPassword(passwordHashingUtil.encodePassword(dto.getPassword()));
        user.setEmail(dto.getEmail());

        UserEntity createdUser = userRepository.save(user);
        // TODO 非同期でメール飛ばす
        return Map.of("userName", createdUser.getUserName(), "email", createdUser.getEmail(), "password", createdUser.getPassword());
    }

    private void checkIsUserUnique(UserRegistrationRequestDto dto){
        if(Boolean.TRUE.equals(userRepository.existsByEmail(dto.getEmail()))){
            throw new YomouException(YomouMessage.EMAIL_NOT_UNIQUE, dto);
        }

        if(Boolean.TRUE.equals(userRepository.existsByUserName(dto.getUserName()))){
            throw new YomouException((YomouMessage.USER_NAME_NOT_UNIQUE), dto);
        }
    }


}
