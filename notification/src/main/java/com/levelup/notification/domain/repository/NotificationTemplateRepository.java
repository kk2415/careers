package com.levelup.notification.domain.repository;

import com.levelup.notification.domain.entity.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
}
