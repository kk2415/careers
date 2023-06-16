package com.levelup.api.scheduler;

import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.service.JobService;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.notification.domain.service.JobNotificationService;
import com.levelup.notification.enumeration.FcmTopicName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class JobScheduler {

    private final List<Crawler> crawlers;
    private final JobService jobService;
    private final JobNotificationService jobNotificationService;

    @Scheduled(cron = "0 0 3 * * *")
    public void crawlingJobs() {
        List<JobVO> jobs = new ArrayList<>();

        crawlers.forEach(crawler -> {
            List<JobVO> crawledJobs = crawler.crawling();
            List<JobVO> newJobs = jobService.saveIfAbsent(crawledJobs, crawler.getCompany());

            List<JobVO> notExistsJobs = jobService.getNotMatched(crawledJobs, crawler.getCompany());
            jobService.deleteAll(notExistsJobs);

            jobs.addAll(newJobs);
        });

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, jobs.stream()
                .map(JobVO::getTitle)
                .toList());
    }
}
