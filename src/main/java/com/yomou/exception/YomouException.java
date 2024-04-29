package com.yomou.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class YomouException extends RuntimeException {
    private final String message;
    private final HttpStatus status;

    public YomouException(YomouMessage message) {
        this.message = message.getMessage();
        this.status = message.getStatus();
    }
}
