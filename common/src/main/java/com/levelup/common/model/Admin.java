package com.levelup.common.model;

import com.levelup.common.jpaentity.AdminEntity;

public record Admin(
        Long id,
        String username,
        String password
) {
    public static Admin from(AdminEntity admin) {
        return new Admin(
                admin.getId(),
                admin.getUsername(),
                admin.getPassword()
        );
    }
}
