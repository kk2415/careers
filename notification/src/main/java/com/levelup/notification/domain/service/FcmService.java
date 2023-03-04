package com.levelup.notification.domain.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.TopicManagementResponse;
import com.levelup.notification.domain.VO.FcmDeviceTokenVO;
import com.levelup.notification.domain.entity.fcm.FcmDeviceToken;
import com.levelup.notification.domain.entity.fcm.FcmTopic;
import com.levelup.notification.domain.exception.EntityNotFoundException;
import com.levelup.notification.domain.exception.ErrorCode;
import com.levelup.notification.domain.repository.FcmDeviceTokenRepository;
import com.levelup.notification.domain.repository.FcmTopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmService {

    @Value("${project.properties.firebase-create-scoped}")
    String fireBaseCreateScoped;

    private final FcmDeviceTokenRepository fcmDeviceTokenRepository;
    private final FcmTopicRepository fcmTopicRepository;
    private FirebaseMessaging firebaseMessaging;

    @PostConstruct
    public void pushNotification() throws IOException {
        InputStream serviceAccount = new ClassPathResource("firebase/level-up-516d8-firebase-adminsdk-2ffux-dc6b1f5357.json").getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(
                        GoogleCredentials
                                .fromStream(serviceAccount)
                                .createScoped(fireBaseCreateScoped)
                )
                .setDatabaseUrl("https://fcm.googleapis.com/v1/projects/level-up-516d8/message:send")
                .setProjectId("level-up-516d8")
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        this.firebaseMessaging = FirebaseMessaging.getInstance(app);
    }

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

    @Transactional
    public FcmDeviceTokenVO saveFcmDeviceToken(String token) {
        Optional<FcmDeviceToken> optionalToken = fcmDeviceTokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            return FcmDeviceTokenVO.from(optionalToken.get());
        }

        FcmDeviceToken savedDeviceToken = fcmDeviceTokenRepository.save(FcmDeviceToken.of(token));

        return FcmDeviceTokenVO.from(savedDeviceToken);
    }

    @Transactional
    public void handleTopicSubscription(Long topicId, String deviceToken) throws FirebaseMessagingException {
        FcmTopic findTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FCM_TOPIC_NOT_FOUND));
        FcmDeviceToken findDeviceToken = fcmDeviceTokenRepository.findByToken(deviceToken)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FCM_DEVICE_TOKEN_NOT_FOUND));

        if (isSubscribeToTopic(topicId, deviceToken)) {
            boolean result = subscribeToTopic(findTopic.getTopicName(), deviceToken);

            if (result) {
                findDeviceToken.subscribeToTopic(findTopic);
            }
        } else {
            boolean result = unsubscribeToTopic(findTopic.getTopicName(), deviceToken);

            if (result) {
                findDeviceToken.subscribeToTopic(null);
            }
        }
    }

    private boolean isSubscribeToTopic(Long topicId, String deviceToken) {
        Optional<FcmDeviceToken> findDeviceToken
                = fcmDeviceTokenRepository.findByTopicIdAndToken(topicId, deviceToken);

        return findDeviceToken.isPresent();
    }

    @Transactional
    public boolean subscribeToTopic(String topic, String deviceToken) throws FirebaseMessagingException {
        TopicManagementResponse response
                = firebaseMessaging.subscribeToTopic(Collections.singletonList(deviceToken), topic);

        if (response.getSuccessCount() == 0) {
            log.error("{} 토큰을 {} 토픽에 등록하지 못했습니다.", deviceToken, topic);

            for (TopicManagementResponse.Error error : response.getErrors()) {
                log.error("reason: {}", error.getReason());
            }

            return false;
        }

        return true;
    }

    @Transactional
    public boolean unsubscribeToTopic(String topic, String deviceToken) throws FirebaseMessagingException {
        TopicManagementResponse response
                = firebaseMessaging.unsubscribeFromTopic(Collections.singletonList(deviceToken), topic);

        if (response.getSuccessCount() == 0) {
            log.error("{} 토큰을 {} 토픽에서 해제하지 못했습니다.", deviceToken, topic);

            for (TopicManagementResponse.Error error : response.getErrors()) {
                log.error("reason: {}", error.getReason());
            }

            return false;
        }

        return true;
    }
}
