package com.levelup.api.controller.v1.notification;

import com.levelup.api.controller.v1.dto.FcmTopicDto;
import com.levelup.api.controller.v1.dto.FcmTopicDto.FcmTopicCreateRequest;
import com.levelup.notification.domain.service.fcm.TopicService;
import com.levelup.notification.domain.vo.FcmTopicVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "FCM 토픽 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm/topics")
@RestController
public class FcmTopicController {

    private final TopicService fcmTopicService;

    @Operation(summary = "FCM 토픽 생성")
    @PostMapping
    public ResponseEntity<FcmTopicVO> createTopic(@RequestParam @Valid FcmTopicCreateRequest request) {
        FcmTopicVO fcmTopicVO = fcmTopicService.saveFcmTopic(request.topicName());

        return ResponseEntity.status(HttpStatus.CREATED).body(fcmTopicVO);
    }

    @Operation(summary = "토픽 구독 토글링", description = "토픽이 구독되어 있으면 해제, 아니면 구독")
    @PostMapping("/handle-subscription")
    public ResponseEntity<Void> handleTopic(
            @RequestParam Long topicId,
            @RequestParam String deviceToken
    ) {
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

    @Operation(summary = "토픽 구독")
    @PatchMapping("/subscription")
    public ResponseEntity<FcmTopicDto.FcmTopicSubscriptionResponse> subscription(
            @RequestParam Long topicId,
            @RequestParam String deviceToken
    ) {
        boolean result = fcmTopicService.subscribeToTopic(topicId, deviceToken);

        return ResponseEntity.ok().body(FcmTopicDto.FcmTopicSubscriptionResponse.from(result));
    }

    @Operation(summary = "토픽 구독 해제")
    @PatchMapping("/unsubscription")
    public ResponseEntity<FcmTopicDto.FcmTopicSubscriptionResponse> unsubscription(
            @RequestParam Long topicId,
            @RequestParam String deviceToken
    ) {
        boolean result = fcmTopicService.unsubscribeToTopic(topicId, deviceToken);

        return ResponseEntity.ok().body(FcmTopicDto.FcmTopicSubscriptionResponse.from(result));
    }
}
