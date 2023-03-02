package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.CoupangScraper;
import com.levelup.job.domain.VO.JobVO;
import com.levelup.job.domain.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("CoupangCrawler")
public class CoupangCrawler implements Crawler {

    private final CoupangScraper coupangScraper;

    @Override
    public Company getCompany() {
        return Company.COUPANG;
    }

    @Override
    public List<JobVO> crawling() {
        return coupangScraper.findJobs();
    }
}

