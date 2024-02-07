package com.levelup.job.controller.v1.notification;

import com.levelup.job.controller.v1.dto.NotificationDto;
import com.levelup.notification.domain.service.JobNotificationService;
import com.levelup.notification.infrastructure.enumeration.FcmTopicName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "알람 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
@RestController
public class NotificationController {

    private final JobNotificationService jobNotificationService;

    @Operation(summary = "새로운 공고 알람 발생")
    @PostMapping
    public ResponseEntity<String> pushNewJobsNotification(@Valid @RequestBody NotificationDto.NotificationRequest request) {
        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, request.notificationBodies());

        return ResponseEntity.ok().build();
    }
}
