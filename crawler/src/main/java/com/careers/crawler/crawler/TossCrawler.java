package com.careers.crawler.crawler;

import com.careers.crawler.crawler.scraper.Scraper;
import com.careers.crawler.domain.enumeration.Company;
import com.careers.crawler.domain.model.Job;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("tossCrawler")
public class TossCrawler implements Crawler<Job> {

    private final Scraper<Job> scraper;

    public TossCrawler(
            @Qualifier("tossScraper") Scraper<Job> scraper
    ) {
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

