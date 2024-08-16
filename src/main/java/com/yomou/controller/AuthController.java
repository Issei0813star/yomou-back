package com.yomou.controller;

import com.yomou.dto.LoginRequestDto;
import com.yomou.dto.VerifyUserEmailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yomou.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginRequestDto request){
        return authService.login(request);
    }

    @PostMapping("/verify")
    public void verifyUserEmail(@RequestBody VerifyUserEmailRequestDto request) {
        authService.verifyUserEmail(request);
    }

}
