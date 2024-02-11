package com.levelup.crawler.scheduler;

import com.levelup.crawler.crawler.Crawler;
import com.levelup.crawler.domain.model.Job;
import com.levelup.crawler.domain.service.JobSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class CrawlingScheduler {

    private final List<Crawler<Job>> crawlers;
    private final JobSendService jobSendService;

    @Scheduled(cron = "0 0 */2 * * *")
    public void crawlingJobs() {
        crawlers.stream()
                .map(Crawler::crawling)
                .forEach(jobSendService::send);
    }
}
