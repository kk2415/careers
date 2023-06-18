package com.levelup.job.scheduler;

import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.service.JobService;
import com.levelup.job.domain.vo.JobVO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableScheduling
public class CrawlingScheduler {

    private final List<Crawler> crawlers;
    private final JobService jobService;

    @Scheduled(cron = "0 */3 * * * *")
    public void crawlingJobs() {
        List<JobVO> jobs = new ArrayList<>();

        crawlers.forEach(crawler -> {
            List<JobVO> crawledJobs = crawler.crawling();
            List<JobVO> newJobs = jobService.saveIfAbsent(crawledJobs, crawler.getCompany());

            List<JobVO> notExistsJobs = jobService.getNotMatched(crawledJobs, crawler.getCompany());
            jobService.deleteAll(notExistsJobs);

            jobs.addAll(newJobs);
        });
    }
}
