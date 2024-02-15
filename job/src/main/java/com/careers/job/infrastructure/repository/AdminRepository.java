package com.careers.job.infrastructure.repository;

import com.careers.job.infrastructure.jpaentity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity, Long> {

    Optional<AdminEntity> findByUsername(String username);
}
