package com.yomou.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {

    @JsonProperty
    private String userName;

    @JsonProperty
    private String password;
}
