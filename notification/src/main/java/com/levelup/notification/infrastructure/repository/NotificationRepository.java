package com.levelup.notification.infrastructure.repository;

import com.levelup.notification.infrastructure.jpaentity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
