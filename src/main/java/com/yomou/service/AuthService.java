package com.yomou.service;

import com.yomou.exception.IncorrectPasswordException;
import com.yomou.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yomou.entity.UserEntity;
import com.yomou.dto.UserDto;
import com.yomou.repository.UserRepository;
import com.yomou.util.PasswordHashingUtil;
import com.yomou.util.JwtGenerator;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordHashingUtil passwordHashingUtil;
    private final JwtGenerator jwtGenerator;

    public ResponseEntity<String> login (UserDto dto){
        try{
            UserDetails user = loadUserByUsername(dto.getUserName());
            verifyPassword(dto.getPassword(), user.getPassword());
            String token = jwtGenerator.generateToken(user);
            return ResponseEntity.ok().body(token);
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

    private void verifyPassword(String plainPassword, String hashedPassword) {
        if (!passwordHashingUtil.verifyPassword(plainPassword, hashedPassword)) {
            throw new IncorrectPasswordException("パスワードが間違っています");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String userName) {

        UserEntity userEntity = findUserByUserName(userName);

        return User.builder()
                .username(userEntity.getUserName())
                .password(userEntity.getPassword())
                .build();
    }
}
