package com.yomou.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;


import com.yomou.dto.UserRegistrationRequestDto;
import com.yomou.service.UserService;
import com.yomou.entity.UserEntity;
import com.yomou.util.JwtGenerator;
import com.yomou.util.PasswordHashingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.yomou.repository.UserRepository;
import org.springframework.http.ResponseEntity;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordHashingUtil passwordHashingUtil;

    @Mock
    private JwtGenerator jwtGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, passwordHashingUtil, jwtGenerator);
    }

    @Test
    public void testCreateUser_WhenEmailIsUnique_ReturnsToken() {

        when(passwordHashingUtil.encodePassword(anyString())).thenReturn("encodedPassword");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);

        UserEntity userEntity = new UserEntity(0L, "test", "password", "unique@test.com");
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        when(jwtGenerator.generateToken(any(UserEntity.class))).thenReturn("testToken");

        UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto("test", "Password", "unique@test.com");

        ResponseEntity<Object> response = userService.createUser(userRegistrationRequestDto);

        assertEquals(200, response.getStatusCode().value());
    }
}

