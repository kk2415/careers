package com.levelup.notification.domain.service;

import com.levelup.notification.domain.vo.FcmDeviceTokenVO;
import com.levelup.notification.domain.vo.FcmTopicVO;
import com.levelup.notification.domain.entity.fcm.FcmDeviceToken;
import com.levelup.notification.domain.entity.fcm.FcmTopic;
import com.levelup.notification.domain.repository.FcmDeviceTokenRepository;
import com.levelup.notification.domain.repository.FcmTopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class FcmService {

    private final FcmDeviceTokenRepository fcmDeviceTokenRepository;
    private final FcmTopicRepository fcmTopicRepository;

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
    public FcmTopicVO saveFcmTopic(String topicName) {
        FcmTopic saveFcmTopic = fcmTopicRepository.save(FcmTopic.of(topicName));

        return FcmTopicVO.from(saveFcmTopic);
    }
}
