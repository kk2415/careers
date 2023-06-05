package com.levelup.api.controller.v1.job;

import com.levelup.api.controller.v1.dto.JobDto;
import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.service.JobService;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.notification.domain.service.JobNotificationService;
import com.levelup.notification.enumeration.FcmTopicName;
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

    private final JobService jobService;
    private final JobNotificationService jobNotificationService;

    @Operation(summary = "카카오 채용 크롤링")
    @PostMapping("/kakao")
    public ResponseEntity<List<JobDto.Response>> crawlKakao() {
        List<JobVO> crawledJobs = kakaoCrawler.crawling();
        List<JobVO> newJobs = jobService.saveIfAbsent(crawledJobs, Company.KAKAO);

        List<JobVO> notExistsJobs = jobService.getNotMatched(crawledJobs, Company.KAKAO);
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(JobVO::getTitle)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "라인 채용 크롤링")
    @PostMapping("/line")
    public ResponseEntity<List<JobDto.Response>> crawlLine() {
        List<JobVO> crawledJobs = lineCrawler.crawling();
        List<JobVO> newJobs = jobService.saveIfAbsent(crawledJobs, Company.LINE);

        List<JobVO> notExistsJobs = jobService.getNotMatched(crawledJobs, Company.LINE);
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(JobVO::getTitle)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "네이버 채용 크롤링")
    @PostMapping("/naver")
    public ResponseEntity<List<JobDto.Response>> crawlNaver() {
        List<JobVO> crawledJobs = naverCrawler.crawling();
        List<JobVO> newJobs = jobService.saveIfAbsent(crawledJobs, Company.NAVER);

        List<JobVO> notExistsJobs = jobService.getNotMatched(crawledJobs, Company.NAVER);
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(JobVO::getTitle)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "쿠팡 채용 크롤링")
    @PostMapping("/coupang")
    public ResponseEntity<List<JobDto.Response>> crawlCoupang() {
        List<JobVO> crawledJobs = coupangCrawler.crawling();
        List<JobVO> newJobs = jobService.saveIfAbsent(crawledJobs, Company.COUPANG);

        List<JobVO> notExistsJobs = jobService.getNotMatched(crawledJobs, Company.COUPANG);
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(JobVO::getTitle)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "토스 채용 크롤링")
    @PostMapping("/toss")
    public ResponseEntity<List<JobDto.Response>> crawlToss() {
        List<JobVO> crawledJobs = tossCrawler.crawling();
        List<JobVO> newJobs = jobService.saveIfAbsent(crawledJobs, Company.TOSS);

        List<JobVO> notExistsJobs = jobService.getNotMatched(crawledJobs, Company.TOSS);
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(JobVO::getTitle)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "배민 채용 크롤링")
    @PostMapping("/bamin")
    public ResponseEntity<List<JobDto.Response>> crawlBamin() {
        List<JobVO> crawledJobs = baminCrawler.crawling();
        List<JobVO> newJobs = jobService.saveIfAbsent(crawledJobs, Company.BAMIN);

        List<JobVO> notExistsJobs = jobService.getNotMatched(crawledJobs, Company.BAMIN);
        jobService.deleteAll(notExistsJobs);

        jobNotificationService.pushNewJobsNotification(FcmTopicName.JOB, newJobs.stream()
                .map(JobVO::getTitle)
                .toList());

        return ResponseEntity.ok().body(newJobs.stream()
                .map(JobDto.Response::from).toList());
    }
}
