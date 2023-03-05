package com.levelup.api.controller.v1.job;

import com.levelup.api.controller.v1.dto.JobDto;
import com.levelup.job.crawler.Crawler;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "채용 사이트 크롤링 API")
@RequestMapping("/api/v1/jobs/crawling")
@RestController
public class CrawlingApiController {

    private final Crawler kakaoCrawler;
    private final Crawler LineCrawler;
    private final Crawler naverCrawler;
    private final Crawler coupangCrawler;
    private final Crawler tossCrawler;

    private final JobService jobService;

    @Autowired
    public CrawlingApiController(
            @Qualifier("KakaoCrawler") Crawler kakaoCrawler,
            @Qualifier("LineCrawler") Crawler lineCrawler,
            @Qualifier("NaverCrawler") Crawler naverCrawler,
            @Qualifier("TossCrawler") Crawler tossCrawler,
            @Qualifier("CoupangCrawler") Crawler coupangCrawler,
            JobService jobService
    ) {
        this.kakaoCrawler = kakaoCrawler;
        this.LineCrawler = lineCrawler;
        this.naverCrawler = naverCrawler;
        this.tossCrawler = tossCrawler;
        this.coupangCrawler = coupangCrawler;
        this.jobService = jobService;
    }

    @Operation(summary = "카카오 채용 크롤링")
    @PostMapping("/kakao")
    public ResponseEntity<List<JobDto.Response>> crawlingKakao() {
        List<JobVO> jobs = kakaoCrawler.crawling();

        List<JobVO> savedJobs = jobService.saveIfAbsent(jobs, Company.KAKAO);

        List<JobVO> notExistsJobs = jobService.getNotMatched(jobs, Company.KAKAO);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "라인 채용 크롤링")
    @PostMapping("/line")
    public ResponseEntity<List<JobDto.Response>> crawlingLine() {
        List<JobVO> jobs = LineCrawler.crawling();

        List<JobVO> savedJobs = jobService.saveIfAbsent(jobs, Company.LINE);

        List<JobVO> notExistsJobs = jobService.getNotMatched(jobs, Company.LINE);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "네이버 채용 크롤링")
    @PostMapping("/naver")
    public ResponseEntity<List<JobDto.Response>> crawlingNaver() {
        List<JobVO> jobs = naverCrawler.crawling();

        List<JobVO> savedJobs = jobService.saveIfAbsent(jobs, Company.NAVER);

        List<JobVO> notExistsJobs = jobService.getNotMatched(jobs, Company.NAVER);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "쿠팡 채용 크롤링")
    @PostMapping("/coupang")
    public ResponseEntity<List<JobDto.Response>> crawlingCoupang() {
        List<JobVO> jobs = coupangCrawler.crawling();

        List<JobVO> savedJobs = jobService.saveIfAbsent(jobs, Company.COUPANG);

        List<JobVO> notExistsJobs = jobService.getNotMatched(jobs, Company.COUPANG);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from).toList());
    }

    @Operation(summary = "토스 채용 크롤링")
    @PostMapping("/toss")
    public ResponseEntity<List<JobDto.Response>> crawlingToss() {
        List<JobVO> jobs = tossCrawler.crawling();

        List<JobVO> savedJobs = jobService.saveIfAbsent(jobs, Company.TOSS);

        List<JobVO> notExistsJobs = jobService.getNotMatched(jobs, Company.TOSS);
        jobService.deleteAll(notExistsJobs);

        return ResponseEntity.ok().body(savedJobs.stream()
                .map(JobDto.Response::from).toList());
    }
}
