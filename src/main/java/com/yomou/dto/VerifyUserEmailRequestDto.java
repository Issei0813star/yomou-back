package com.yomou.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VerifyUserEmailRequestDto {

    @JsonProperty
    private String userEmail;

    @JsonProperty
    private String verificationCode;
}
