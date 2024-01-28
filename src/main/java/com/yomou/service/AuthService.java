package com.yomou.service;

import com.yomou.exception.IncorrectPasswordException;
import com.yomou.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yomou.entity.UserEntity;
import com.yomou.dto.UserDto;
import com.yomou.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public ResponseEntity<String> login (UserDto dto){
        try{
            UserEntity user = findUserByUserName(dto.getUserName());
            checkPassword(dto, user);
            return ResponseEntity.ok("TODO トークン返す");
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ユーザーが存在しません。: " + dto.getUserName());
        }
        catch (IncorrectPasswordException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("パスワードが間違っています。");
        }
    }

    private UserEntity findUserByUserName(String userName){
        Optional<UserEntity> userOptional = userRepository.findByUserName(userName);
        return userOptional.orElseThrow(() -> new UserNotFoundException("ユーザーが存在しません: " + userName));
    }

    private void checkPassword(UserDto dto, UserEntity user){
        if (!dto.getPassword().equals(user.getPassword())){
            throw new IncorrectPasswordException("パスワードが間違っています");
        }
    }
}
