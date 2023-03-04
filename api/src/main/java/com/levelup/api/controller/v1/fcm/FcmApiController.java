package com.levelup.api.controller.v1.fcm;

import com.levelup.notification.domain.VO.FcmDeviceTokenVO;
import com.levelup.notification.domain.VO.FcmTopicVO;
import com.levelup.notification.domain.entity.fcm.FcmTopic;
import com.levelup.notification.domain.exception.EntityNotFoundException;
import com.levelup.notification.domain.exception.ErrorCode;
import com.levelup.notification.domain.repository.FcmTopicRepository;
import com.levelup.notification.domain.service.fcm.FcmSendMessageService;
import com.levelup.notification.domain.service.fcm.FcmService;
import com.levelup.notification.domain.service.fcm.FcmTopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FCM API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm")
@RestController
public class FcmApiController {

    private final FcmService fcmService;
    private final FcmTopicService fcmTopicService;
    private final FcmSendMessageService fcmSendMessageService;
    private final FcmTopicRepository fcmTopicRepository;

    @Operation(summary = "FCM 토픽 생성")
    @PostMapping("/topic")
    public ResponseEntity<FcmTopicVO> createTopic(@RequestParam String topicName) {
        FcmTopicVO fcmTopicVO = fcmService.saveFcmTopic(topicName);

        return ResponseEntity.status(HttpStatus.CREATED).body(fcmTopicVO);
    }

    @Operation(summary = "FCM 디바이스 토큰 DB에 저장")
    @PostMapping("/device-token")
    public ResponseEntity<FcmDeviceTokenVO> createDeviceToken(@RequestParam String token) {
        FcmDeviceTokenVO fcmDeviceTokenVO = fcmService.saveFcmDeviceToken(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(fcmDeviceTokenVO);
    }

    @Operation(summary = "토픽 구독 관리", description = "토픽이 구독되어 있으면 해제, 아니면 구독")
    @PostMapping("/handle-topic-subscription")
    public ResponseEntity<Void> handleTopic(@RequestParam Long topicId, @RequestParam String deviceToken) {
        fcmTopicService.handleTopicSubscription(topicId, deviceToken);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "테스트 임시 API")
    @PostMapping("/topic/message")
    public ResponseEntity<Void> sendMessageToTopic(@RequestParam Long topicId) {
        FcmTopic findFcmTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FCM_TOPIC_NOT_FOUND));

        fcmSendMessageService.sendMessageToTopic(
                findFcmTopic.getTopicName(),
                "test message",
                "test message body");

        return ResponseEntity.ok().build();
    }
}
