package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.CarrotMarketScraper;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("carrotMarketCrawler")
public class CarrotMarketCrawler implements Crawler {

    private final CarrotMarketScraper scraper;

    @Override
    public Company getCompany() {
        return Company.CARROT_MARKET;
    }

    @Override
    public List<JobVO> crawling() {
        return scraper.scrape();
    }
}

