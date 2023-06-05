package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.TossScraper;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("tossCrawler")
public class TossCrawler implements Crawler {

    private final TossScraper tossScraper;

    @Override
    public Company getCompany() {
        return Company.TOSS;
    }

    @Override
    public List<JobVO> crawling() {
        return tossScraper.findJobs();
    }
}

