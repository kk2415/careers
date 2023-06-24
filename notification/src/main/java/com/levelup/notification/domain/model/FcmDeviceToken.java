package com.levelup.notification.domain.model;

import com.levelup.notification.infrastructure.jpaentity.fcm.FcmDeviceTokenEntity;

public record FcmDeviceToken(
        Long id,
        String token,
        String topicName
) {
    public static FcmDeviceToken of(Long id, String token, String topicName) {
        return new FcmDeviceToken(id, token, topicName);
    }

    public static FcmDeviceToken from(FcmDeviceTokenEntity fcmDeviceToken) {
        return new FcmDeviceToken(
                fcmDeviceToken.getId(),
                fcmDeviceToken.getToken(),
                fcmDeviceToken.getTopic() == null ? "" : fcmDeviceToken.getTopic().getTopicName());
    }
}
