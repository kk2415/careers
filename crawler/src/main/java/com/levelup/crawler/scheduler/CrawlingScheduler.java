package com.levelup.crawler.scheduler;

import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.model.CreateJob;
import com.levelup.job.domain.service.JobService;
import com.levelup.job.domain.model.Job;
import com.levelup.job.web.api.NotificationApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class CrawlingScheduler {

    private final List<Crawler<CreateJob>> crawlers;
    private final JobService jobService;
    private final NotificationApiClient notificationApiClient;

    @Scheduled(cron = "0 0 */2 * * *")
    public void crawlingJobs() {
        List<String> newJobsSubject = crawlers.stream()
                .map(crawler -> {
                    List<CreateJob> crawledJobs = crawler.crawling();
                    List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, crawler.getCompany());

                    List<Job> deleteJobs = jobService.getNotMatched(crawledJobs, crawler.getCompany());
                    jobService.deleteAll(deleteJobs);

                    return newJobs;
                })
                .flatMap(Collection::stream)
                .map(Job::getSubject)
                .toList();

        notificationApiClient.sendPushAlarm(newJobsSubject);
    }
}
