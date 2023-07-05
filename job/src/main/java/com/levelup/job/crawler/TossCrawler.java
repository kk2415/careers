package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.Scraper;
import com.levelup.job.domain.model.Job;
import com.levelup.job.infrastructure.enumeration.Company;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tossCrawler")
public class TossCrawler implements Crawler {

    private final Scraper<Job> scraper;

    public TossCrawler(@Qualifier("tossScraper") Scraper<Job> scraper) {
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

