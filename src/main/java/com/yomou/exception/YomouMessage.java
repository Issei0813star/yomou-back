package com.yomou.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum YomouMessage {

    USER_NOT_FOUND("ユーザーが存在しません。", HttpStatus.NOT_FOUND),
    INCORRECT_PASSWORD("パスワードが間違っています。", HttpStatus.UNAUTHORIZED);

    private String message;
    private HttpStatus status;

    private YomouMessage(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

}
