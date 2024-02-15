package com.careers.crawler.scheduler;

import com.careers.crawler.crawler.Crawler;
import com.careers.crawler.domain.model.Job;
import com.careers.crawler.domain.service.JobSendService;
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
