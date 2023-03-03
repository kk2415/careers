package com.levelup.notification.domain.VO;

import com.levelup.notification.domain.entity.Notification;
import com.levelup.notification.domain.enumeration.NotificationTemplateType;
import com.levelup.notification.domain.enumeration.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class NotificationVO {

    private Long id;
    private String title;
    private Long receiverId;
    private Long activatorId; /* member who occur notification */
    private LocalDate readAt;
    private Boolean isRead;
    private NotificationType notificationType;
    private NotificationTemplateType templateType;
    private NotificationTemplateVO template;

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
        Notification entity = Notification.of(
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

        return entity;
    }
}
