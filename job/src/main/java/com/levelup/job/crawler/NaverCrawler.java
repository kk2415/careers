package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.NaverScraper;
import com.levelup.job.domain.model.Job;
import com.levelup.job.infrastructure.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("naverCrawler")
public class NaverCrawler implements Crawler {

    private final NaverScraper scraper;

    @Override
    public Company getCompany() {
        return scraper.company;
    }

    @Override
    public List<Job> crawling() {
        return scraper.scrape();
    }
}

