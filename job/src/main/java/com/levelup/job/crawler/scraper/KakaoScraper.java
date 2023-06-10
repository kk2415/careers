package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class KakaoScraper {

    private final static Company company = Company.KAKAO;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        int page = 1;
        int lastPage = 5;
        String params;

        WebDriver driver = prototypeBeanProvider.getObject();
        List<JobVO> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            params = "?skilset=Android,iOS,Windows,Web_front,DB,Cloud,Server,Hadoop_eco_system,Algorithm_Ranking,System&company=ALL&page=" + page;
            driver.get(company.getUrl() + params);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> jobList = driver.findElements(By.cssSelector("ul.list_jobs a"));
            List<JobVO> scrapedJobs = jobList.stream().map(job -> {
                String title = job.findElement(By.cssSelector("h4.tit_jobs")).getText();
                final String url = job.getAttribute("href");
                final String noticeEndDate = job.findElement(By.cssSelector("dl.list_info > dd")).getText();

                return JobVO.of(title, company, url, noticeEndDate);
            }).toList();

            jobs.addAll(scrapedJobs);
        }

        driver.quit();

        return jobs;
    }
}
