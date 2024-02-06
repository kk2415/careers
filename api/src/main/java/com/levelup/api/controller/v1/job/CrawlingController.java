package com.levelup.api.controller.v1.job;

import com.levelup.api.controller.v1.dto.JobDto;
import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.model.CreateJob;
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

    private final Crawler<CreateJob> kakaoCrawler;
    private final Crawler<CreateJob> lineCrawler;
    private final Crawler<CreateJob> naverCrawler;
    private final Crawler<CreateJob> coupangCrawler;
    private final Crawler<CreateJob> tossCrawler;
    private final Crawler<CreateJob> baminCrawler;
    private final Crawler<CreateJob> carrotMarketCrawler;
    private final Crawler<CreateJob> bucketPlaceCrawler;
    private final Crawler<CreateJob> yanoljaCrawler;
    private final Crawler<CreateJob> skCrawler;
    private final Crawler<CreateJob> socarCrawler;

    private final JobService jobService;
    private final JobNotificationService jobNotificationService;

    @Operation(summary = "카카오 채용 크롤링")
    @PostMapping("/kakao")
    public ResponseEntity<List<JobDto.Response>> crawlKakao() {
        List<CreateJob> crawledJobs = kakaoCrawler.crawling();
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
        List<CreateJob> crawledJobs = lineCrawler.crawling();
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
        List<CreateJob> crawledJobs = naverCrawler.crawling();
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
        List<CreateJob> crawledJobs = coupangCrawler.crawling();
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
        List<CreateJob> crawledJobs = tossCrawler.crawling();
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
        List<CreateJob> crawledJobs = baminCrawler.crawling();
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
        List<CreateJob> crawledJobs = carrotMarketCrawler.crawling();
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
        List<CreateJob> crawledJobs = bucketPlaceCrawler.crawling();
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
        List<CreateJob> crawledJobs = yanoljaCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, yanoljaCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, yanoljaCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "SK 채용 크롤링")
    @PostMapping("/sk")
    public ResponseEntity<List<JobDto.Response>> crawlSkt() {
        List<CreateJob> crawledJobs = skCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, skCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, skCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "쏘카 채용 크롤링")
    @PostMapping("/socar")
    public ResponseEntity<List<JobDto.Response>> crawlSocar() {
        List<CreateJob> crawledJobs = socarCrawler.crawling();
        List<Job> newJobs = jobService.saveIfAbsent(crawledJobs, socarCrawler.getCompany());

        List<Job> notExistsJobs = jobService.getNotMatched(crawledJobs, socarCrawler.getCompany());
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(Job::getSubject)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }
}
