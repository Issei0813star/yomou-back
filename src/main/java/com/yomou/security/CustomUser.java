package com.yomou.security;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.emptyList;


@Getter
public class CustomUser extends User {

    private final String email;

    public CustomUser(String userName, String email) {
        super(userName, "", emptyList());
        this.email = email;
    }

}
