package com.levelup.notification.domain.entity;

import com.levelup.notification.domain.enumeration.NotificationTemplateType;
import com.levelup.notification.domain.enumeration.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "notification")
@Entity
public class Notification {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String title;
    private Long receiverId;
    private Long activatorId; /* member who occur notification */
    private LocalDate readAt;
    private Boolean isRead;
    private NotificationType notificationType;
    private NotificationTemplateType templateType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_template_id")
    private NotificationTemplate template;

    public static Notification of(
            Long id,
            String title,
            Long receiverId,
            Long activatorId,
            LocalDate readAt,
            Boolean isRead,
            NotificationType notificationType,
            NotificationTemplateType templateType,
            NotificationTemplate template)
    {
        return new Notification(
                id,
                title,
                receiverId,
                activatorId,
                readAt,
                isRead,
                notificationType,
                templateType,
                template);
    }
}
