package com.levelup.job.crawler.scraper;

import com.levelup.api.ApiApplication;
import com.levelup.job.domain.model.Job;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@ContextConfiguration(classes = ApiApplication.class)
@SpringBootTest
public class KakaoScraperTest {

    private final KakaoScraper kakaoScraper;

    public KakaoScraperTest(@Autowired KakaoScraper kakaoScraper) {
        this.kakaoScraper = kakaoScraper;
    }

    @Test
    public void executeScrap() {
        List<Job> jobs = kakaoScraper.scrape();
        jobs.forEach(System.out::println);
    }
}
