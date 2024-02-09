package com.levelup.crawler.domain.service;

import com.levelup.crawler.domain.model.Job;
import com.levelup.crawler.domain.model.ExternalJob;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JobSendService {

    @Value("${job.url}")
    private String jobServerApiUrl;

    private final WebClient webClient;

    public void send(List<Job> jobs) {
        final List<ExternalJob.Request> requests = jobs.stream()
                .map(job -> ExternalJob.Request.of(
                        job.title(),
                        job.company(),
                        job.url(),
                        job.noticeEndDate(),
                        job.jobGroup()
                ))
                .toList();

        webClient.post()
                .uri(jobServerApiUrl + "/api/v1/jobs/create-drop")
                .body(Mono.just(ExternalJob.Requests.from(requests)), ExternalJob.Requests.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ExternalJob.Response>>() {})
                .block();
    }
}
