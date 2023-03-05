package com.levelup.api.controller.v1.dto;

import com.levelup.notification.domain.enumeration.FcmTopicName;
import com.levelup.notification.domain.vo.FcmTopicVO;

import javax.validation.constraints.NotNull;

public class FcmTopicDto {

    public record FcmTopicCreateRequest(
            @NotNull
            FcmTopicName topicName
    ) {

    }

    public record FcmTopicResponse(
            Long id,
            String topicName
    ) {
        public static FcmTopicResponse of(Long id, String topicName) {
            return new FcmTopicResponse(id, topicName);
        }

        public static FcmTopicResponse from(FcmTopicVO VO) {
            return new FcmTopicResponse(VO.id(), VO.topicName());
        }
    }
}
