package com.levelup.notification.domain.vo;

import com.levelup.notification.domain.entity.fcm.FcmTopic;

public record FcmTopicVO(
        Long id,
        String topicName
) {
    public static FcmTopicVO of(Long id, String topicName) {
        return new FcmTopicVO(id, topicName);
    }

    public static FcmTopicVO from(FcmTopic fcmTopic) {
        return new FcmTopicVO(fcmTopic.getId(), fcmTopic.getTopicName());
    }
}
