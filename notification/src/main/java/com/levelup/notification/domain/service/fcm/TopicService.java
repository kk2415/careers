package com.levelup.notification.domain.service.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import com.levelup.notification.domain.enumeration.FcmTopicName;
import com.levelup.notification.domain.vo.FcmTopicVO;
import com.levelup.notification.domain.entity.fcm.FcmDeviceToken;
import com.levelup.notification.domain.entity.fcm.FcmTopic;
import com.levelup.notification.domain.exception.EntityNotFoundException;
import com.levelup.notification.domain.exception.ErrorCode;
import com.levelup.notification.domain.repository.FcmDeviceTokenRepository;
import com.levelup.notification.domain.repository.FcmTopicRepository;
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
    public FcmTopicVO saveFcmTopic(FcmTopicName topicName) {
        FcmTopic saveFcmTopic = fcmTopicRepository.save(FcmTopic.of(topicName.name()));

        return FcmTopicVO.from(saveFcmTopic);
    }

    @Transactional
    public void handleTopicSubscription(Long topicId, String deviceToken) {
        FcmTopic findTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FCM_TOPIC_NOT_FOUND));
        FcmDeviceToken findDeviceToken = findFcmDeviceTokenOrCreate(deviceToken);

        if (isSubscribeToTopic(topicId, deviceToken)) {
            boolean result = unsubscribeToTopic(findTopic.getTopicName(), deviceToken);

            if (result) {
                findDeviceToken.subscribeToTopic(null);
            }
        } else {
            boolean result = subscribeToTopic(findTopic.getTopicName(), deviceToken);

            if (result) {
                findDeviceToken.subscribeToTopic(findTopic);
            }
        }
    }

    @Transactional
    public FcmDeviceToken findFcmDeviceTokenOrCreate(String deviceToken) {
        return fcmDeviceTokenRepository.findByToken(deviceToken)
                .orElseGet(() -> fcmDeviceTokenRepository.save(FcmDeviceToken.of(deviceToken)));
    }

    private boolean isSubscribeToTopic(Long topicId, String deviceToken) {
        Optional<FcmDeviceToken> findDeviceToken
                = fcmDeviceTokenRepository.findByTopicIdAndToken(topicId, deviceToken);

        return findDeviceToken.isPresent();
    }

    @Transactional
    public boolean subscribeToTopic(Long topicId, String deviceToken) {
        FcmTopic findFcmTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FCM_TOPIC_NOT_FOUND));
        FcmDeviceToken findDeviceToken = findFcmDeviceTokenOrCreate(deviceToken);

        boolean result = subscribeToTopic(findFcmTopic.getTopicName(), deviceToken);
        if (result) {
            findDeviceToken.subscribeToTopic(findFcmTopic);
        }

        return result;
    }

    @Transactional
    public boolean subscribeToTopic(String topic, String deviceToken) {
        TopicManagementResponse response;

        try {
            response = firebaseMessaging.subscribeToTopic(Collections.singletonList(deviceToken), topic);
        } catch (Exception e) {
            log.error("{} 토큰을 {} 토픽에 등록하지 못했습니다. {}", deviceToken, topic, e.getMessage());
            return false;
        }

        if (response.getSuccessCount() == 0) {
            String reason = response.getErrors().stream()
                    .map(TopicManagementResponse.Error::getReason)
                    .collect(Collectors.joining(", "));

            log.error("{} 토큰을 {} 토픽에 등록하지 못했습니다. {}", deviceToken, topic, reason);

            return false;
        }

        return true;
    }

    @Transactional
    public boolean unsubscribeToTopic(Long topicId, String deviceToken) {
        FcmTopic findFcmTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FCM_TOPIC_NOT_FOUND));
        FcmDeviceToken findDeviceToken = findFcmDeviceTokenOrCreate(deviceToken);

        boolean result = unsubscribeToTopic(findFcmTopic.getTopicName(), deviceToken);
        if (result) {
            findDeviceToken.subscribeToTopic(null);
        }

        return unsubscribeToTopic(findFcmTopic.getTopicName(), deviceToken);
    }

    @Transactional
    public boolean unsubscribeToTopic(String topic, String deviceToken) {
        TopicManagementResponse response;

        try {
            response = firebaseMessaging.unsubscribeFromTopic(Collections.singletonList(deviceToken), topic);
        } catch (Exception e) {
            log.error("{} 토큰을 {} 토픽에서 해제하지 못했습니다. {}", deviceToken, topic, e.getMessage());
            return false;
        }

        if (response.getSuccessCount() == 0) {
            String reason = response.getErrors().stream()
                    .map(TopicManagementResponse.Error::getReason)
                    .collect(Collectors.joining(", "));

            log.error("{} 토큰을 {} 토픽에서 해제하지 못했습니다. {}", deviceToken, topic, reason);

            return false;
        }

        return true;
    }

    public List<FcmTopicVO> getAll() {
        return fcmTopicRepository.findAll().stream()
                .map(FcmTopicVO::from)
                .toList();
    }
}
