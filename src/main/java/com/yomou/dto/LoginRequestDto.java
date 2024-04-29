package com.yomou.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class LoginRequestDto {

    @NonNull
    @JsonProperty
    /*
     * email or userName
     */
    private String userId;

    @NonNull
    @JsonProperty
    private String password;

}
