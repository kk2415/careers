package com.levelup.notification.domain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.levelup.notification.domain.VO.NotificationVO;
import com.levelup.notification.domain.entity.Notification;
import com.levelup.notification.domain.enumeration.NotificationType;
import com.levelup.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Service
public class NotificationService {

    @Value("${project.properties.firebase-create-scoped}")
    String fireBaseCreateScoped;

    @Value("${project.properties.firebase-topic}")
    String topic;

    private final ObjectMapper objectMapper;
    /**
     * 이 클래스는 모든 서버측 Firebase 클라우드 메시징 작업의 시작점입니다.
     * */
    private FirebaseMessaging firebaseMessaging;

    private final NotificationRepository notificationRepository;

    @PostConstruct
    public void pushNotification() throws IOException {
        InputStream serviceAccount = new ClassPathResource("firebase/level-up-516d8-firebase-adminsdk-2ffux-34f85d5ae0.json").getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials
                        .fromStream(serviceAccount)
                        .createScoped(fireBaseCreateScoped))
                .setDatabaseUrl("https://fcm.googleapis.com/v1/projects/level-up-516d8/message:send")
                .setProjectId("level-up-516d8")
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        this.firebaseMessaging = FirebaseMessaging.getInstance(app);
    }

    public NotificationVO create(NotificationVO notificationVO) {
        Notification notification = notificationVO.toEntity();
        notificationRepository.save(notification);

        if (!NotificationType.NONE.equals(notificationVO.getNotificationType())) {
            //TODO:: 알림 발송 TO ANDROID, IOS, SMS, EMAIL
        }

        return NotificationVO.from(notification);
    }

    public void push(NotificationVO notificationVO) {
        com.google.firebase.messaging.Notification firebaseMessagingNotification
                = com.google.firebase.messaging.Notification.builder()
                .setTitle(notificationVO.getTemplate().getTitle())
                .setBody(notificationVO.getTemplate().getBody())
                .build();

        Message.Builder builder = Message.builder();
        Message message = builder
                .setNotification(firebaseMessagingNotification)
                .setToken("cwgb74IiTbaIomTqftMMpg:APA91bGwUNQebusyx52Yydr2NucxDFXkCm0hgw0ekoi_9hvCrkpyyjiXiVnLZiA9zfFpYX-Ru0u5DW1g9YzdmgDj-OHaOaf4sNxCGyCgIAHBgrxtdWYnyPxZkjd-qKUVd9TNaECk4xyD")
                .build();

        try {
            String messageId = this.firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
