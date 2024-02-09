package com.levelup.crawler.crawler;

import com.levelup.crawler.crawler.scraper.Scraper;
import com.levelup.crawler.domain.enumeration.Company;
import com.levelup.crawler.domain.model.Job;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("kakaoCrawler")
public class KakaoCrawler implements Crawler<Job> {

    private final Scraper<Job> scraper;

    public KakaoCrawler(
            @Qualifier("kakaoScraper") Scraper<Job> scraper
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

