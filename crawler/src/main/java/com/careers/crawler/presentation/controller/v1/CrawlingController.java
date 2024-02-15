package com.careers.crawler.presentation.controller.v1;

import com.careers.crawler.domain.service.JobSendService;
import com.careers.crawler.presentation.controller.v1.dto.JobDto;
import com.careers.crawler.crawler.Crawler;
import com.careers.crawler.domain.model.Job;
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

    private final Crawler<Job> kakaoCrawler;
    private final Crawler<Job> lineCrawler;
    private final Crawler<Job> naverCrawler;
    private final Crawler<Job> tossCrawler;
    private final Crawler<Job> baminCrawler;
    private final Crawler<Job> carrotMarketCrawler;
    private final Crawler<Job> bucketPlaceCrawler;
    private final Crawler<Job> yanoljaCrawler;
    private final Crawler<Job> skCrawler;
    private final Crawler<Job> socarCrawler;

    private final JobSendService jobSendService;

    @Operation(summary = "카카오 채용 크롤링")
    @PostMapping("/kakao")
    public ResponseEntity<List<JobDto.Response>> crawlKakao() {
        final List<Job> crawledJobs = kakaoCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "라인 채용 크롤링")
    @PostMapping("/line")
    public ResponseEntity<List<JobDto.Response>> crawlLine() {
        final List<Job> crawledJobs = lineCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "네이버 채용 크롤링")
    @PostMapping("/naver")
    public ResponseEntity<List<JobDto.Response>> crawlNaver() {
        final List<Job> crawledJobs = naverCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "토스 채용 크롤링")
    @PostMapping("/toss")
    public ResponseEntity<List<JobDto.Response>> crawlToss() {
        final List<Job> crawledJobs = tossCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "배민 채용 크롤링")
    @PostMapping("/bamin")
    public ResponseEntity<List<JobDto.Response>> crawlBamin() {
        final List<Job> crawledJobs = baminCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "당근마켓 채용 크롤링")
    @PostMapping("/carrot-market")
    public ResponseEntity<List<JobDto.Response>> crawlCarrotMarket() {
        final List<Job> crawledJobs = carrotMarketCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "오늘의 집 채용 크롤링")
    @PostMapping("/bucket-place")
    public ResponseEntity<List<JobDto.Response>> crawlBucketPlace() {
        final List<Job> crawledJobs = bucketPlaceCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "야놀자 채용 크롤링")
    @PostMapping("/yanolja")
    public ResponseEntity<List<JobDto.Response>> crawlYanolja() {
        final List<Job> crawledJobs = yanoljaCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "SK 채용 크롤링")
    @PostMapping("/sk")
    public ResponseEntity<List<JobDto.Response>> crawlSkt() {
        final List<Job> crawledJobs = skCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }

    @Operation(summary = "쏘카 채용 크롤링")
    @PostMapping("/socar")
    public ResponseEntity<List<JobDto.Response>> crawlSocar() {
        final List<Job> crawledJobs = socarCrawler.crawling();
        jobSendService.send(crawledJobs);

        return ResponseEntity.ok().body(crawledJobs.stream()
                .map(JobDto.Response::from)
                .toList());
    }
}
