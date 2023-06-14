package com.levelup.api.security.userdetails;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum LoginType {

    NONE,
    ATTENDER,
    ADMIN
    ;

    @JsonCreator
    public static LoginType match(String target) {
        for (LoginType loginType : LoginType.values()) {
            if (loginType.name().equals(target)) {
                return loginType;
            }
        }

        return ATTENDER;
    }
}
