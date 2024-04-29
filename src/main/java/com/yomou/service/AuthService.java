package com.yomou.service;

import com.yomou.dto.LoginDto;
import com.yomou.exception.YomouException;
import com.yomou.exception.YomouMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.yomou.entity.UserEntity;
import com.yomou.repository.UserRepository;
import com.yomou.util.PasswordHashingUtil;
import com.yomou.util.JwtGenerator;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordHashingUtil passwordHashingUtil;
    private final JwtGenerator jwtGenerator;

    public Object login (LoginDto request){

        UserEntity user = findUser(request.getUserId());
        if(Objects.isNull(user)){
            throw new YomouException(YomouMessage.USER_NOT_FOUND, request);
        }
        if(Boolean.FALSE.equals(verifyPassword(request.getPassword(), user.getPassword()))){
            throw new YomouException(YomouMessage.INCORRECT_PASSWORD, request);
        }
        String token =  jwtGenerator.generateToken(user);
        return Map.of("token", token, "userName", user.getUserName(), "email", user.getEmail());
    }

    /**
     * @param userId String
     * @return UserEntity
     */
    private UserEntity findUser(String userId){
        return  userRepository.findUser(userId);
    }

    private Boolean verifyPassword(String plainPassword, String hashedPassword) {
        return passwordHashingUtil.verifyPassword(plainPassword, hashedPassword);
    }
}
