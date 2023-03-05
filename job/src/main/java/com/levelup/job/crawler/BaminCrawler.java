package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.BaminScraper;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("BaminCrawler")
public class BaminCrawler implements Crawler {

    private final BaminScraper baminScraper;

    @Override
    public Company getCompany() {
        return Company.BAMIN;
    }

    @Override
    public List<JobVO> crawling() {
        return baminScraper.findJobs();
    }
}

