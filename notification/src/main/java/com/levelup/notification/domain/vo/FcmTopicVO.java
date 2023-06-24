package com.levelup.notification.domain.vo;

import com.levelup.notification.infrastructure.jpaentity.fcm.FcmTopicEntity;

public record FcmTopicVO(
        Long id,
        String topicName
) {
    public static FcmTopicVO of(Long id, String topicName) {
        return new FcmTopicVO(id, topicName);
    }

    public static FcmTopicVO from(FcmTopicEntity fcmTopic) {
        return new FcmTopicVO(fcmTopic.getId(), fcmTopic.getTopicName());
    }
}
