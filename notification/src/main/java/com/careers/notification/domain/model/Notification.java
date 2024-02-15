package com.careers.notification.domain.model;

import com.careers.notification.infrastructure.jpaentity.NotificationEntity;

import java.time.LocalDate;

public record Notification(
        Long id,
        String title,
        String body,
        Long receiverId,
        Long activatorId /* member who occur notification */,
        LocalDate readAt,
        Boolean isRead
) {
    public static Notification of(
            String title,
            String body,
            Long receiverId,
            Long activatorId)
    {
        return new Notification(
                null,
                title,
                body,
                receiverId,
                activatorId,
                null,
                false);
    }

    public static Notification from(NotificationEntity entity) {
        return new Notification(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getReceiverId(),
                entity.getActivatorId(),
                entity.getReadAt(),
                entity.getIsRead());
    }
}
