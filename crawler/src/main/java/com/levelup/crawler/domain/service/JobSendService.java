package com.levelup.crawler.domain.service;

import com.levelup.crawler.domain.model.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JobSendService {

    @Value("${api_base_url}")
    private String apiBaseUrl;

    private final WebClient webClient;

    public void send(List<Job> jobs) {
        webClient.post()
                .uri(apiBaseUrl + "/api/v1/jobs")
                .body(Mono.just(jobs), List.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
