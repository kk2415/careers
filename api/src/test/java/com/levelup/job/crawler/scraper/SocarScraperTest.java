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
public class SocarScraperTest {

    private final SocarScraper socarScraper;

    public SocarScraperTest(@Autowired SocarScraper socarScraper) {
        this.socarScraper = socarScraper;
    }

    @Test
    public void executeScrap() {
        List<Job> jobs = socarScraper.scrape();
        jobs.forEach(System.out::println);
    }
}
