package com.levelup.api.controller.v1.fcm;

import com.levelup.notification.domain.vo.FcmDeviceTokenVO;
import com.levelup.notification.domain.entity.fcm.FcmTopic;
import com.levelup.notification.domain.exception.EntityNotFoundException;
import com.levelup.notification.domain.exception.ErrorCode;
import com.levelup.notification.domain.repository.FcmTopicRepository;
import com.levelup.notification.domain.service.fcm.SendMessageService;
import com.levelup.notification.domain.service.FcmService;
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
    private final SendMessageService fcmSendMessageService;
    private final FcmTopicRepository fcmTopicRepository;

    @Operation(summary = "FCM 디바이스 토큰 DB에 저장")
    @PostMapping("/device-token")
    public ResponseEntity<FcmDeviceTokenVO> createDeviceToken(@RequestParam String token) {
        FcmDeviceTokenVO fcmDeviceTokenVO = fcmService.saveFcmDeviceToken(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(fcmDeviceTokenVO);
    }

    @Operation(summary = "테스트 임시 API ^^")
    @PostMapping("/topic/message")
    public ResponseEntity<Void> test(@RequestParam Long topicId) {
        FcmTopic findFcmTopic = fcmTopicRepository.findById(topicId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorCode.FCM_TOPIC_NOT_FOUND));

        fcmSendMessageService.sendMessageToTopic(
                findFcmTopic.getTopicName(),
                "test message",
                "test message body");

        return ResponseEntity.ok().build();
    }
}
