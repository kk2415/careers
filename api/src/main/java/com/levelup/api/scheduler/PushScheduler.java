package com.levelup.api.scheduler;

import com.levelup.job.domain.service.JobService;
import com.levelup.job.domain.model.Job;
import com.levelup.notification.domain.service.JobNotificationService;
import com.levelup.notification.infrastructure.enumeration.FcmTopicName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class PushScheduler {

    private final JobService jobService;
    private final JobNotificationService jobNotificationService;

    @Scheduled(cron = "0 0 */1 * * *")
    public void crawlingJobs() {
        List<Job> notPushedJobs = jobService.getNotPushedJobs();
        List<Job> jobs = jobService.push(notPushedJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, jobs.stream()
                .map(Job::getSubject)
                .toList());
    }
}
