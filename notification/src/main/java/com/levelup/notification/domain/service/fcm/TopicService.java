package com.levelup.notification.domain.service.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import com.levelup.common.exception.EntityNotFoundException;
import com.levelup.common.exception.ExceptionCode;
import com.levelup.notification.infrastructure.enumeration.FcmTopicName;
import com.levelup.notification.domain.model.FcmTopic;
import com.levelup.notification.infrastructure.jpaentity.fcm.FcmDeviceTokenEntity;
import com.levelup.notification.infrastructure.jpaentity.fcm.FcmTopicEntity;
import com.levelup.notification.infrastructure.repository.FcmDeviceTokenRepository;
import com.levelup.notification.infrastructure.repository.FcmTopicRepository;
import com.levelup.notification.infrastructure.enumeration.FcmTopicSubscription;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicService {

    private final FcmDeviceTokenRepository fcmDeviceTokenRepository;
    private final FcmTopicRepository fcmTopicRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Transactional
    public FcmTopic saveFcmTopic(FcmTopicName topicName) {
        FcmTopicEntity saveFcmTopic = fcmTopicRepository.save(FcmTopicEntity.of(topicName.name()));

        return FcmTopic.from(saveFcmTopic);
    }

    @Transactional
    public FcmTopicSubscription handleTopicSubscription(Long topicId, String deviceToken) {
        FcmTopicEntity findTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.FCM_TOPIC_NOT_FOUND));
        FcmDeviceTokenEntity findDeviceToken = findFcmDeviceTokenOrCreate(deviceToken);

        if (isSubscribeToTopic(topicId, deviceToken)) {
            FcmTopicSubscription result = unsubscribeToTopic(findTopic.getTopicName(), deviceToken);

            if (FcmTopicSubscription.UNSUBSCRIPTION.equals(result)) {
                findDeviceToken.subscribeToTopic(null);
                return FcmTopicSubscription.UNSUBSCRIPTION;
            }

            return FcmTopicSubscription.SUBSCRIPTION;
        }

        FcmTopicSubscription result = subscribeToTopic(findTopic.getTopicName(), deviceToken);
        if (FcmTopicSubscription.SUBSCRIPTION.equals(result)) {
            findDeviceToken.subscribeToTopic(findTopic);
            return FcmTopicSubscription.SUBSCRIPTION;
        }

        return FcmTopicSubscription.UNSUBSCRIPTION;
    }

    @Transactional
    public FcmDeviceTokenEntity findFcmDeviceTokenOrCreate(String deviceToken) {
        return fcmDeviceTokenRepository.findByToken(deviceToken)
                .orElseGet(() -> fcmDeviceTokenRepository.save(FcmDeviceTokenEntity.of(deviceToken)));
    }

    private boolean isSubscribeToTopic(Long topicId, String deviceToken) {
        Optional<FcmDeviceTokenEntity> findDeviceToken
                = fcmDeviceTokenRepository.findByTopicIdAndToken(topicId, deviceToken);

        return findDeviceToken.isPresent();
    }

    @Transactional
    public FcmTopicSubscription subscribeToTopic(Long topicId, String deviceToken) {
        FcmTopicEntity findFcmTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.FCM_TOPIC_NOT_FOUND));
        FcmDeviceTokenEntity findDeviceToken = findFcmDeviceTokenOrCreate(deviceToken);

        FcmTopicSubscription result = subscribeToTopic(findFcmTopic.getTopicName(), deviceToken);
        if (FcmTopicSubscription.SUBSCRIPTION.equals(result)) {
            findDeviceToken.subscribeToTopic(findFcmTopic);
        }

        return result;
    }

    @Transactional
    public FcmTopicSubscription subscribeToTopic(String topic, String deviceToken) {
        TopicManagementResponse response;

        try {
            response = firebaseMessaging.subscribeToTopic(Collections.singletonList(deviceToken), topic);
        } catch (Exception e) {
            log.error("{} 토큰을 {} 토픽에 등록하지 못했습니다. {}", deviceToken, topic, e.getMessage());
            return FcmTopicSubscription.UNSUBSCRIPTION;
        }

        if (response.getSuccessCount() == 0) {
            String reason = response.getErrors().stream()
                    .map(TopicManagementResponse.Error::getReason)
                    .collect(Collectors.joining(", "));

            log.error("{} 토큰을 {} 토픽에 등록하지 못했습니다. {}", deviceToken, topic, reason);

            return FcmTopicSubscription.UNSUBSCRIPTION;
        }

        return FcmTopicSubscription.SUBSCRIPTION;
    }

    @Transactional
    public FcmTopicSubscription unsubscribeToTopic(Long topicId, String deviceToken) {
        FcmTopicEntity findFcmTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionCode.FCM_TOPIC_NOT_FOUND));
        FcmDeviceTokenEntity findDeviceToken = findFcmDeviceTokenOrCreate(deviceToken);

        FcmTopicSubscription result = unsubscribeToTopic(findFcmTopic.getTopicName(), deviceToken);
        if (FcmTopicSubscription.UNSUBSCRIPTION.equals(result)) {
            findDeviceToken.subscribeToTopic(null);
        }

        return result;
    }

    @Transactional
    public FcmTopicSubscription unsubscribeToTopic(String topic, String deviceToken) {
        TopicManagementResponse response;

        try {
            response = firebaseMessaging.unsubscribeFromTopic(Collections.singletonList(deviceToken), topic);
        } catch (Exception e) {
            log.error("{} 토큰을 {} 토픽에서 해제하지 못했습니다. {}", deviceToken, topic, e.getMessage());
            return FcmTopicSubscription.SUBSCRIPTION;
        }

        if (response.getSuccessCount() == 0) {
            String reason = response.getErrors().stream()
                    .map(TopicManagementResponse.Error::getReason)
                    .collect(Collectors.joining(", "));

            log.error("{} 토큰을 {} 토픽에서 해제하지 못했습니다. {}", deviceToken, topic, reason);

            return FcmTopicSubscription.SUBSCRIPTION;
        }

        return FcmTopicSubscription.UNSUBSCRIPTION;
    }

    public FcmTopicSubscription getFcmTopicSubscription(String deviceToken) {
        FcmDeviceTokenEntity fcmDeviceToken = fcmDeviceTokenRepository.findByToken(deviceToken).orElse(null);

        if (fcmDeviceToken == null || fcmDeviceToken.getTopic() == null) {
            return FcmTopicSubscription.UNSUBSCRIPTION;
        }
        return FcmTopicSubscription.SUBSCRIPTION;
    }

    public List<FcmTopic> getAll() {
        return fcmTopicRepository.findAll().stream()
                .map(FcmTopic::from)
                .toList();
    }
}
