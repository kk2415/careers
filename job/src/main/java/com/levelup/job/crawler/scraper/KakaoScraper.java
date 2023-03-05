package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.vo.KakaoJobVO;
import com.levelup.job.domain.enumeration.Company;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KakaoScraper {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    public List<JobVO> findJobs() {
        int page = 1;
        int lastPage = 5;
        String params;

        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();

        List<JobVO> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            params = "?skilset=Android,iOS,Windows,Web_front,DB,Cloud,Server,Hadoop_eco_system,Algorithm_Ranking,System&company=ALL&page=" + page;

            driver.get(Company.KAKAO.getUrl() + params);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> jobList = driver.findElements(By.cssSelector("ul.list_jobs a"));
            List<KakaoJobVO> scrapedJobs = jobList.stream().map(job -> {
                String title = job.findElement(By.cssSelector("h4.tit_jobs")).getText();
                final String url = job.getAttribute("href");
                final String noticeEndDate = job.findElement(By.cssSelector("dl.list_info > dd")).getText();

                return KakaoJobVO.of(title, url, noticeEndDate);
            }).toList();

            jobs.addAll(scrapedJobs);
        }

        driver.quit();

        return jobs;
    }
}
