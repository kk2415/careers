package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.Scraper;
import com.levelup.job.domain.model.Job;
import com.levelup.job.infrastructure.enumeration.Company;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("skCrawler")
public class SKCrawler implements Crawler<Job> {

    private final Scraper<Job> scraper;

    public SKCrawler(@Qualifier("SKScraper") Scraper<Job> scraper) {
        this.scraper = scraper;
    }

    @Override
    public Company getCompany() {
        return scraper.getCompany();
    }

    @Override
    public List<Job> crawling() {
        return scraper.scrape();
    }
}

