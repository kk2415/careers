package com.levelup.notification.infrastructure.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum FcmTopicName {

    NONE,
    JOB,
    ;

    @JsonCreator
    public static FcmTopicName match(String target) {
        return Arrays.stream(FcmTopicName.values())
                .filter(topicName -> topicName.name().equals(target))
                .findAny()
                .orElse(NONE);
    }
}
