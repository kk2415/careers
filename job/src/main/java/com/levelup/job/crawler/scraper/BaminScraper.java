package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.vo.JobVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BaminScraper {

    public List<JobVO> findJobs() {
        return List.of();

//        Elements jobList = jsoupTemplate.select("div.recruit-list > ul.recruit-type-list li");
//
//        return jobList.stream().map(jobElement -> {
//            String title = jsoupTemplate.selectSub(jobElement, "a.title > p.fr-view").text();
//            String url = jsoupTemplate.selectSub(jobElement, "a.title").attr("href");
//            String noticeEndDate = jsoupTemplate.selectSub(jobElement, "div.flag-type:nth-child(2)").text();
//
//            return BaminJobVO.of(title, url, noticeEndDate);
//        }).collect(Collectors.toUnmodifiableList());
    }
}
