package com.levelup.api.security.userdetails.vo;

import com.levelup.api.security.userdetails.jpaentity.Admin;

public record AdminVO(
        Long id,
        String username,
        String password
) {
    public static AdminVO from(Admin admin) {
        return new AdminVO(
                admin.getId(),
                admin.getUsername(),
                admin.getPassword()
        );
    }
}
