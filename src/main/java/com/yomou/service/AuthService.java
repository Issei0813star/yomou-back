package com.yomou.service;

import com.yomou.exception.IncorrectPasswordException;
import com.yomou.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yomou.entity.UserEntity;
import com.yomou.dto.UserRequest;
import com.yomou.repository.UserRepository;
import com.yomou.util.PasswordHashingUtil;
import com.yomou.util.JwtGenerator;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService{

    private final UserRepository userRepository;
    private final PasswordHashingUtil passwordHashingUtil;
    private final JwtGenerator jwtGenerator;

    public ResponseEntity<Object> login (UserRequest dto){
        try{
            UserEntity user = findUserByEmail(dto.getEmail());
            verifyPassword(dto.getPassword(), user.getPassword());
            String token = jwtGenerator.generateToken(user);
            return ResponseEntity.ok().body(Map.of("token", token));
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("errorMessage", "ユーザーが存在しません。: " + dto.getUserName()));
        }
        catch (IncorrectPasswordException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("errorMessage", "パスワードが間違っています。"));
        }
    }

    private UserEntity findUserByEmail(String email){
        Optional<UserEntity> userOptional = userRepository.findByEmail(email);
        return userOptional.orElseThrow(() -> new UserNotFoundException("ユーザーが存在しません: " + email));
    }

    private void verifyPassword(String plainPassword, String hashedPassword) {
        if (!passwordHashingUtil.verifyPassword(plainPassword, hashedPassword)) {
            throw new IncorrectPasswordException("パスワードが間違っています");
        }
    }
}
