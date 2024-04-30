package com.yomou.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import java.util.Map;

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
    public void testCreateUser_WhenUnique_Success() {
        when(passwordHashingUtil.encodePassword(anyString())).thenReturn("encodedPassword");

        when(userRepository.existsByEmail("unique@test.com")).thenReturn(false);
        when(userRepository.existsByUserName("unique")).thenReturn(false);

        UserEntity savedUserEntity = new UserEntity(1L, "unique", "encodedPassword", "unique@test.com");
        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);

        UserRegistrationRequestDto userRegistrationRequestDto = new UserRegistrationRequestDto("unique", "Password", "unique@test.com");
        Map<String, Object> createdUser = userService.createUser(userRegistrationRequestDto);

        assertNotNull(createdUser);
        assertEquals("unique", createdUser.get("userName"));
        assertEquals("unique@test.com", createdUser.get("email"));
        assertEquals("encodedPassword", createdUser.get("password"));
    }

}

