package com.yomou.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum YomouMessage {

    private String message;
    private HttpStatus status;

    private YomouMessage(String message, HttpStatus status){
        this.message = message;
        this.status = status;
    }

}
