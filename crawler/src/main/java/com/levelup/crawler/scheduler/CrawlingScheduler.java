package com.levelup.crawler.scheduler;

import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.service.JobService;
import com.levelup.job.domain.model.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class CrawlingScheduler {

    private final List<Crawler> crawlers;
    private final JobService jobService;

    @Scheduled(cron = "0 0 */2 * * *")
    public void crawlingJobs() {
        crawlers.forEach(crawler -> {
            List<Job> crawledJobs = crawler.crawling();
            jobService.saveIfAbsent(crawledJobs, crawler.getCompany());

            List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, crawler.getCompany());
            jobService.deleteAll(notExistsJobs);
        });
    }
}
