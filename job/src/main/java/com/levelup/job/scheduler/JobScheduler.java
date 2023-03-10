package com.levelup.job.scheduler;

import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.service.JobService;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.web.api.NotificationApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JobScheduler {

    private final List<Crawler> crawlers;
    private final JobService jobService;
    private final NotificationApiClient notificationApiClient;

    @Scheduled(cron = "0 0 0 0 */1 * ")
    public void crawlingJobs() {
        List<JobVO> newJobs = new ArrayList<>();

        crawlers.forEach(crawler -> {
            List<JobVO> crawledJobs = crawler.crawling();

            List<JobVO> savedJobs = jobService.saveIfAbsent(crawledJobs, crawler.getCompany());
            newJobs.addAll(savedJobs);

            List<JobVO> deleteJobs = jobService.getNotMatched(crawledJobs, crawler.getCompany());
            jobService.deleteAll(deleteJobs);
        });

        //TODO:: 새로운 공고 알림
        notificationApiClient.sendPushAlarm(newJobs.stream()
                .map(JobVO::getTitle)
                .toList());
    }
}
