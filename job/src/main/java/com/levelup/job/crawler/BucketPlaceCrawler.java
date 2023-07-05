package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.Scraper;
import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.domain.model.Job;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("bucketPlaceCrawler")
public class BucketPlaceCrawler implements Crawler {

    private final Scraper<Job> scraper;

    public BucketPlaceCrawler(@Qualifier("bucketPlaceScraper") Scraper<Job> scraper) {
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
