package com.levelup.notification.domain.service;

import com.levelup.notification.domain.VO.NotificationVO;
import com.levelup.notification.domain.entity.Notification;
import com.levelup.notification.domain.enumeration.NotificationType;
import com.levelup.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationVO create(NotificationVO notificationVO) {
        Notification notification = notificationVO.toEntity();
        notificationRepository.save(notification);

        if (!NotificationType.NONE.equals(notificationVO.notificationType())) {
            //TODO:: 알림 발송 TO ANDROID, IOS, SMS, EMAIL
        }

        return NotificationVO.from(notification);
    }
}
