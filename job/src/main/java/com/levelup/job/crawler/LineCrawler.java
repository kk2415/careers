package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.LineScraper;
import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("lineCrawler")
public class LineCrawler implements Crawler {

    private final LineScraper lineScraper;

    @Override
    public Company getCompany() {
        return Company.LINE;
    }

    @Override
    public List<JobVO> crawling() {
        return lineScraper.findJobs();
    }
}

