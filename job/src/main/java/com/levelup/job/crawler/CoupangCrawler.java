package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.Scraper;
import com.levelup.job.domain.model.CreateJob;
import com.levelup.job.infrastructure.enumeration.Company;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("coupangCrawler")
public class CoupangCrawler implements Crawler<CreateJob> {

    private final Scraper<CreateJob> scraper;

    public CoupangCrawler(
            @Qualifier("coupangScraper") Scraper<CreateJob> scraper
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

