package com.levelup.crawler.crawler;

import com.levelup.crawler.crawler.scraper.Scraper;
import com.levelup.job.domain.model.CreateJob;
import com.levelup.job.infrastructure.enumeration.Company;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tossCrawler")
public class TossCrawler implements Crawler<CreateJob> {

    private final Scraper<CreateJob> scraper;

    public TossCrawler(
            @Qualifier("tossScraper") Scraper<CreateJob> scraper
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

