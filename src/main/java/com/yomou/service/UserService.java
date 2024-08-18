package com.yomou.service;

import com.yomou.dto.UserRegistrationRequestDto;
import com.yomou.entity.User;
import com.yomou.exception.YomouException;
import com.yomou.exception.YomouMessage;
import com.yomou.repository.UserRepository;
import com.yomou.service.async.AsyncSendGridService;
import com.yomou.util.PasswordHashingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordHashingUtil passwordHashingUtil;
    private final AsyncSendGridService sendGridService;

    public Map<String, Object> createUser(UserRegistrationRequestDto dto) {
        checkIsUserUnique(dto);
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setPassword(passwordHashingUtil.encodePassword(dto.getPassword()));
        user.setEmail(dto.getEmail());

        User createdUser = userRepository.save(user);
        sendGridService.asyncSendEmail(createdUser);
        return Map.of("userId", createdUser.getId(), "userName", createdUser.getUserName(), "email", createdUser.getEmail(), "password", createdUser.getPassword());
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
