package com.levelup.notification.infrastructure.jpaentity;

import com.levelup.notification.infrastructure.jpaentity.base.BaseTimeEntity;
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
public class Notification extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String title;
    private String body;
    private Long receiverId;
    private Long activatorId; /* member who occur notification */
    private LocalDate readAt;
    private Boolean isRead;

    public static Notification of(
            Long id,
            String title,
            String body,
            Long receiverId,
            Long activatorId,
            LocalDate readAt,
            Boolean isRead
    ) {
        return new Notification(
                id,
                title,
                body,
                receiverId,
                activatorId,
                readAt,
                isRead
        );
    }
}
