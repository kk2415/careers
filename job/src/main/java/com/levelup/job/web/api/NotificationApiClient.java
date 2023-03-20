package com.levelup.job.web.api;

import com.levelup.job.web.api.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NotificationApiClient {

    private final WebClient webClient;

    public void sendPushAlarm(List<String> bodies) {
        NotificationDto.NotificationRequest request = NotificationDto.NotificationRequest.from(bodies);

        webClient.post()
                .uri("/api/v1/notifications")
                .body(Mono.just(request), NotificationDto.NotificationRequest.class)
                .retrieve();
    }
}
