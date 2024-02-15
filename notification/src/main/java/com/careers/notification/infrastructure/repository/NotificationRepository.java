package com.careers.notification.infrastructure.repository;

import com.careers.notification.infrastructure.jpaentity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
}
