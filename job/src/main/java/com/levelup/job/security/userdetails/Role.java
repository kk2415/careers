package com.levelup.job.security.userdetails;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {

    ANONYMOUS,
    ADMIN,
    ;

    @Override
    public String toString() {
        return "ROLE_" + super.toString().toUpperCase();
    }

    @JsonCreator
    public static Role match(String target) {
        for (Role role : Role.values()) {
            if (role.name().equals(target) || role.toString().equals(target)) {
                return role;
            }
        }

        return ANONYMOUS;
    }
}
