package com.careers.job.domain.model;

import com.careers.job.infrastructure.jpaentity.AdminEntity;

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
