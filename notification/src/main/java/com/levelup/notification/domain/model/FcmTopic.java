package com.levelup.notification.domain.model;

import com.levelup.notification.infrastructure.jpaentity.fcm.FcmTopicEntity;

public record FcmTopic(
        Long id,
        String topicName
) {
    public static FcmTopic of(Long id, String topicName) {
        return new FcmTopic(id, topicName);
    }

    public static FcmTopic from(FcmTopicEntity fcmTopic) {
        return new FcmTopic(fcmTopic.getId(), fcmTopic.getTopicName());
    }
}
