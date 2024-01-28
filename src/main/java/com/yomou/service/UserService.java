package com.yomou.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.yomou.repository.UserRepository;
import com.yomou.dto.UserDto;
import com.yomou.entity.UserEntity;

@Service
@RequiredArgsConstructor

public class UserService {

    private final UserRepository userRepository;

    public UserEntity createUser(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setUserName(dto.getUserName());
        entity.setPassword(dto.getPassword());

        return userRepository.save(entity);
    }

}
