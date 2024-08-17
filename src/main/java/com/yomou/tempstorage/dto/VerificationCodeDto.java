package com.yomou.tempstorage.dto;

import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Data
public class VerificationCodeDto {

    @NonNull
    private String verificationCode;
    @NonNull
    private Instant expiration;

    public VerificationCodeDto(String verificationCode, Instant expiration) {
        this.verificationCode = verificationCode;
        this.expiration = expiration;
    }
}
