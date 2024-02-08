package com.levelup.job.domain.model;

import com.levelup.job.infrastructure.jpaentity.AdminEntity;

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
