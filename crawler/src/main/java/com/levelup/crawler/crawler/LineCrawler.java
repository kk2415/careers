package com.levelup.crawler.crawler;

import com.levelup.crawler.crawler.scraper.Scraper;
import com.levelup.crawler.domain.enumeration.Company;
import com.levelup.crawler.domain.model.CreateJob;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("lineCrawler")
public class LineCrawler implements Crawler<CreateJob> {

    private final Scraper<CreateJob> scraper;

    public LineCrawler(
            @Qualifier("lineScraper") Scraper<CreateJob> scraper
    ) {
        this.scraper = scraper;
    }

    @Override
    public Company getCompany() {
        return scraper.getCompany();
    }

    @Override
    public List<CreateJob> crawling() {
        return scraper.scrape();
    }
}

