package com.yomou.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserRequest {

    @NonNull
    @JsonProperty
    private String userName;

    @NonNull
    @JsonProperty
    private String password;

    @NonNull
    @JsonProperty
    private String email;
}
