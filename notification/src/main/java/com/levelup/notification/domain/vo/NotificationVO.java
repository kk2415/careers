package com.levelup.notification.domain.vo;

import com.levelup.notification.infrastructure.jpaentity.Notification;

import java.time.LocalDate;

public record NotificationVO(
        Long id,
        String title,
        String body,
        Long receiverId,
        Long activatorId /* member who occur notification */,
        LocalDate readAt,
        Boolean isRead
) {
    public static NotificationVO of(
            String title,
            String body,
            Long receiverId,
            Long activatorId)
    {
        return new NotificationVO(
                null,
                title,
                body,
                receiverId,
                activatorId,
                null,
                false);
    }

    public static NotificationVO from(Notification entity) {
        return new NotificationVO(
                entity.getId(),
                entity.getTitle(),
                entity.getBody(),
                entity.getReceiverId(),
                entity.getActivatorId(),
                entity.getReadAt(),
                entity.getIsRead());
    }
}
