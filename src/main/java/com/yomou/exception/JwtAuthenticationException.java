package com.yomou.exception;

public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(){
        super("認証エラー");
    }
}
