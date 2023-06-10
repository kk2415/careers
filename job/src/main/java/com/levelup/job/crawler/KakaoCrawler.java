package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.KakaoScraper;
import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("kakaoCrawler")
public class KakaoCrawler implements Crawler {

    private final KakaoScraper scraper;

    @Override
    public Company getCompany() {
        return Company.KAKAO;
    }

    @Override
    public List<JobVO> crawling() {
        return scraper.scrape();
    }
}

