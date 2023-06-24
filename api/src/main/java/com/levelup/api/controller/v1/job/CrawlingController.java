package com.levelup.api.controller.v1.job;

import com.levelup.api.controller.v1.dto.JobDto;
import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.service.JobService;
import com.levelup.job.domain.model.Job;
import com.levelup.notification.domain.service.JobNotificationService;
import com.levelup.notification.infrastructure.enumeration.FcmTopicName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "채용 사이트 크롤링 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/jobs/crawling")
@RestController
public class CrawlingController {

    private final Crawler kakaoCrawler;
    private final Crawler lineCrawler;
    private final Crawler naverCrawler;
    private final Crawler coupangCrawler;
    private final Crawler tossCrawler;
    private final Crawler baminCrawler;
    private final Crawler carrotMarketCrawler;
    private final Crawler bucketPlaceCrawler;
    private final Crawler yanoljaCrawler;

    private final JobService jobService;
    private final JobNotificationService jobNotificationService;

    @Operation(summary = "카카오 채용 크롤링")
    @PostMapping("/kakao")
    public ResponseEntity<List<JobDto.Response>> crawlKakao() {
        List<Job> crawledJobs = kakaoCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, kakaoCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, kakaoCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "라인 채용 크롤링")
    @PostMapping("/line")
    public ResponseEntity<List<JobDto.Response>> crawlLine() {
        List<Job> crawledJobs = lineCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, lineCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, lineCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "네이버 채용 크롤링")
    @PostMapping("/naver")
    public ResponseEntity<List<JobDto.Response>> crawlNaver() {
        List<Job> crawledJobs = naverCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, naverCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, naverCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "쿠팡 채용 크롤링")
    @PostMapping("/coupang")
    public ResponseEntity<List<JobDto.Response>> crawlCoupang() {
        List<Job> crawledJobs = coupangCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, coupangCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, coupangCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "토스 채용 크롤링")
    @PostMapping("/toss")
    public ResponseEntity<List<JobDto.Response>> crawlToss() {
        List<Job> crawledJobs = tossCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, tossCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, tossCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "배민 채용 크롤링")
    @PostMapping("/bamin")
    public ResponseEntity<List<JobDto.Response>> crawlBamin() {
        List<Job> crawledJobs = baminCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, baminCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, baminCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "당근마켓 채용 크롤링")
    @PostMapping("/carrot-market")
    public ResponseEntity<List<JobDto.Response>> crawlCarrotMarket() {
        List<Job> crawledJobs = carrotMarketCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, carrotMarketCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, carrotMarketCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "오늘의 집 채용 크롤링")
    @PostMapping("/bucket-place")
    public ResponseEntity<List<JobDto.Response>> crawlBucketPlace() {
        List<Job> crawledJobs = bucketPlaceCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, bucketPlaceCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, bucketPlaceCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "야놀자 채용 크롤링")
    @PostMapping("/yanolja")
    public ResponseEntity<List<JobDto.Response>> crawlYanolja() {
        List<Job> crawledJobs = yanoljaCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, yanoljaCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, yanoljaCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }
}
