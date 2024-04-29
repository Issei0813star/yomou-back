package com.yomou.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
public class YomouException extends RuntimeException {
    private final String message;
    private final HttpStatus status;
    private final Object causedBy;
    private String errorCode;

    public YomouException(YomouMessage message, Object causedBy) {
        this.message = message.getMessage();
        this.status = message.getStatus();
        this.causedBy = causedBy;
        this.errorCode = message.name();
    }
}
