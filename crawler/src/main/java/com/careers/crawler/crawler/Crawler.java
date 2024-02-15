package com.careers.crawler.crawler;

import com.careers.crawler.domain.enumeration.Company;

import java.util.List;

public interface Crawler<T> {
    Company getCompany();
    List<T> crawling();
}
