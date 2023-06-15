package com.levelup.common.vo;

import com.levelup.common.jpaentity.Admin;

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
