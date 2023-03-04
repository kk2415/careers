package com.levelup.notification.domain.service.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmSendMessageService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendMessageToSpecificToken(String token, String title, String body) {
        com.google.firebase.messaging.Notification notification
                = com.google.firebase.messaging.Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message.Builder builder = Message.builder();
        Message message = builder
                .setNotification(notification)
                .setToken(token)
                .build();

        try {
            this.firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageToTopic(String topic, String title, String body) {
        com.google.firebase.messaging.Notification notification
                = com.google.firebase.messaging.Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message.Builder builder = Message.builder();
        Message message = builder
                .setNotification(notification)
                .setTopic(topic)
                .build();

        try {
            this.firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }
}
