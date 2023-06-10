package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.BucketPlaceScraper;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("bucketPlaceCrawler")
public class BucketPlaceCrawler implements Crawler {

    private final BucketPlaceScraper scraper;

    @Override
    public Company getCompany() {
        return Company.BUCKET_PLACE;
    }

    @Override
    public List<JobVO> crawling() {
        return scraper.scrape();
    }
}
