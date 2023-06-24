package com.levelup.notification.domain.vo;

import com.levelup.notification.infrastructure.jpaentity.fcm.FcmDeviceToken;

public record FcmDeviceTokenVO(
        Long id,
        String token,
        String topicName
) {
    public static FcmDeviceTokenVO of(Long id, String token, String topicName) {
        return new FcmDeviceTokenVO(id, token, topicName);
    }

    public static FcmDeviceTokenVO from(FcmDeviceToken fcmDeviceToken) {
        return new FcmDeviceTokenVO(
                fcmDeviceToken.getId(),
                fcmDeviceToken.getToken(),
                fcmDeviceToken.getTopic() == null ? "" : fcmDeviceToken.getTopic().getTopicName());
    }
}
