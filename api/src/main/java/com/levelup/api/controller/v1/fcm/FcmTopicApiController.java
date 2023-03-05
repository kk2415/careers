package com.levelup.api.controller.v1.fcm;

import com.levelup.api.controller.v1.dto.FcmTopicDto;
import com.levelup.notification.domain.vo.FcmTopicVO;
import com.levelup.notification.domain.service.fcm.DeviceTokenService;
import com.levelup.notification.domain.service.fcm.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "FCM 토픽 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm/topics")
@RestController
public class FcmTopicApiController {

    private final DeviceTokenService deviceTokenService;
    private final TopicService fcmTopicService;

    @Operation(summary = "FCM 토픽 생성")
    @PostMapping
    public ResponseEntity<FcmTopicVO> createTopic(@RequestParam String topicName) {
        FcmTopicVO fcmTopicVO = fcmTopicService.saveFcmTopic(topicName);

        return ResponseEntity.status(HttpStatus.CREATED).body(fcmTopicVO);
    }

    @Operation(summary = "토픽 구독 관리", description = "토픽이 구독되어 있으면 해제, 아니면 구독")
    @PostMapping("/handle-topic-subscription")
    public ResponseEntity<Void> handleTopic(@RequestParam Long topicId, @RequestParam String deviceToken) {
        fcmTopicService.handleTopicSubscription(topicId, deviceToken);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "FCM 토픽 전체 조회")
    @GetMapping("/all")
    public ResponseEntity<List<FcmTopicDto.FcmTopicResponse>> getAll() {
        List<FcmTopicVO> topics = fcmTopicService.getAll();

        return ResponseEntity.ok().body(topics.stream()
                .map(FcmTopicDto.FcmTopicResponse::from)
                .toList());
    }
}
