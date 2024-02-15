package com.careers.crawler.crawler;

import com.careers.crawler.crawler.scraper.Scraper;
import com.careers.crawler.domain.enumeration.Company;
import com.careers.crawler.domain.model.Job;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

//@Component("coupangCrawler")
public class CoupangCrawler implements Crawler<Job> {

    private final Scraper<Job> scraper;

    public CoupangCrawler(
            @Qualifier("coupangScraper") Scraper<Job> scraper
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

