package com.levelup.notification.domain.VO;

import com.levelup.notification.domain.VO.notificationTemplate.NotificationTemplateVO;
import com.levelup.notification.domain.entity.Notification;
import com.levelup.notification.domain.enumeration.NotificationTemplateType;
import com.levelup.notification.domain.enumeration.NotificationType;

import java.time.LocalDate;

public record NotificationVO(
        Long id,
        String title,
        Long receiverId,
        Long activatorId /* member who occur notification */,
        LocalDate readAt,
        Boolean isRead,
        NotificationType notificationType,
        NotificationTemplateType templateType,
        NotificationTemplateVO template
) {
    public static NotificationVO of(
            Long receiverId,
            Long activatorId,
            NotificationType notificationType,
            NotificationTemplateType templateType,
            NotificationTemplateVO template)
    {
        return new NotificationVO(
                null,
                "title",
                receiverId,
                activatorId,
                null,
                false,
                notificationType,
                templateType,
                template);
    }

    public static NotificationVO from(Notification entity) {
        return new NotificationVO(
                entity.getId(),
                entity.getTitle(),
                entity.getReceiverId(),
                entity.getActivatorId(),
                entity.getReadAt(),
                entity.getIsRead(),
                entity.getNotificationType(),
                entity.getTemplateType(),
                NotificationTemplateVO.from(entity.getTemplate()));
    }

    public Notification toEntity() {
        return Notification.of(
                this.id,
                this.title,
                this.receiverId,
                this.activatorId,
                this.readAt,
                this.isRead,
                this.notificationType,
                this.templateType,
                this.template.toEntity()
        );
    }
}
