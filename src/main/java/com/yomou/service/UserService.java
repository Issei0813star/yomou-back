package com.yomou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.yomou.repository.UserRepository;
import com.yomou.dto.UserDto;
import com.yomou.entity.UserEntity;
import com.yomou.util.PasswordHashingUtil;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;
    private final PasswordHashingUtil passwordHashingUtil;

    public UserEntity createUser(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setUserName(dto.getUserName());
        entity.setPassword(passwordHashingUtil.encodePassword(dto.getPassword()));

        return userRepository.save(entity);
    }

}
