package com.levelup.crawler.scheduler;

import com.levelup.crawler.crawler.Crawler;
import com.levelup.crawler.domain.model.CreateJob;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class CrawlingScheduler {

    private final List<Crawler<CreateJob>> crawlers;

    @Scheduled(cron = "0 0 */2 * * *")
    public void crawlingJobs() {
        crawlers.stream()
                .map(Crawler::crawling)
                .forEach(System.out::println);
    }
}
