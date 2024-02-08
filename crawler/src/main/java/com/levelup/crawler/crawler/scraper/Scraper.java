package com.levelup.crawler.crawler.scraper;

import com.levelup.crawler.domain.enumeration.Company;

import java.util.List;

public interface Scraper<T> {
    List<T> scrape();
    Company getCompany();
}
