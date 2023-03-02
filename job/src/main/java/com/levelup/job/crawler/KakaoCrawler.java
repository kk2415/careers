package com.levelup.job.crawler;

import com.levelup.job.crawler.scraper.KakaoScraper;
import com.levelup.job.domain.VO.JobVO;
import com.levelup.job.domain.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component("KakaoCrawler")
public class KakaoCrawler implements Crawler {

    private final KakaoScraper kakaoScraper;

    @Override
    public Company getCompany() {
        return Company.KAKAO;
    }

    @Override
    public List<JobVO> crawling() {
        return kakaoScraper.findJobs();
    }
}

