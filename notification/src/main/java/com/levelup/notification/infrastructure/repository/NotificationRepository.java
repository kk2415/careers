package com.levelup.notification.infrastructure.repository;

import com.levelup.notification.infrastructure.jpaentity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
