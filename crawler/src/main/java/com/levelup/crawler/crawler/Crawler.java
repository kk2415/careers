package com.levelup.crawler.crawler;

import com.levelup.job.infrastructure.enumeration.Company;

import java.util.List;

public interface Crawler<T> {
    Company getCompany();
    List<T> crawling();
}
