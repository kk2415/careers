package com.levelup.crawler.presentation.controller.v1;

import com.levelup.crawler.presentation.controller.v1.dto.JobDto;
import com.levelup.crawler.crawler.Crawler;
import com.levelup.crawler.domain.model.CreateJob;
import com.levelup.crawler.domain.model.Job;
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

    @Operation(summary = "카카오 채용 크롤링")
    @PostMapping("/kakao")
    public ResponseEntity<List<JobDto.Response>> crawlKakao() {
        List<CreateJob> crawledJobs = kakaoCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "라인 채용 크롤링")
    @PostMapping("/line")
    public ResponseEntity<List<JobDto.Response>> crawlLine() {
        List<CreateJob> crawledJobs = lineCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "네이버 채용 크롤링")
    @PostMapping("/naver")
    public ResponseEntity<List<JobDto.Response>> crawlNaver() {
        List<CreateJob> crawledJobs = naverCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "쿠팡 채용 크롤링")
    @PostMapping("/coupang")
    public ResponseEntity<List<JobDto.Response>> crawlCoupang() {
        List<CreateJob> crawledJobs = coupangCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "토스 채용 크롤링")
    @PostMapping("/toss")
    public ResponseEntity<List<JobDto.Response>> crawlToss() {
        List<CreateJob> crawledJobs = tossCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "배민 채용 크롤링")
    @PostMapping("/bamin")
    public ResponseEntity<List<JobDto.Response>> crawlBamin() {
        List<CreateJob> crawledJobs = baminCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "당근마켓 채용 크롤링")
    @PostMapping("/carrot-market")
    public ResponseEntity<List<JobDto.Response>> crawlCarrotMarket() {
        List<CreateJob> crawledJobs = carrotMarketCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "오늘의 집 채용 크롤링")
    @PostMapping("/bucket-place")
    public ResponseEntity<List<JobDto.Response>> crawlBucketPlace() {
        List<CreateJob> crawledJobs = bucketPlaceCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "야놀자 채용 크롤링")
    @PostMapping("/yanolja")
    public ResponseEntity<List<JobDto.Response>> crawlYanolja() {
        List<CreateJob> crawledJobs = yanoljaCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "SK 채용 크롤링")
    @PostMapping("/sk")
    public ResponseEntity<List<JobDto.Response>> crawlSkt() {
        List<CreateJob> crawledJobs = skCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "쏘카 채용 크롤링")
    @PostMapping("/socar")
    public ResponseEntity<List<JobDto.Response>> crawlSocar() {
        List<CreateJob> crawledJobs = socarCrawler.crawling();

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(createJob -> Job.of(
                        createJob.getTitle(),
                        createJob.getCompany(),
                        createJob.getUrl(),
                        createJob.getNoticeEndDate()
                ))
                .map(JobDto.Response::from).toList());
    }
}
