package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.vo.CoupangJobVO;
import com.levelup.job.domain.vo.JobVO;
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
public class CoupangScraper {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    public List<JobVO> findJobs() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();

        int page = 1;
        int lastPage = 5;
        String params;

        List<JobVO> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            params = "?search=Software+engineer+backend+frontend&location=Seoul%2C+South+Korea&location=South+Korea&pagesize=20#results&page=" + page;
            driver.get(Company.COUPANG.getUrl() + params);

            List<WebElement> elements = driver.findElements(By.cssSelector("div.job-listing > div.card-job"));
            List<CoupangJobVO> scrapedJobs = elements.stream().map((element) -> {
                final String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
                final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getAttribute("href");
                final String noticeEndDate = "채용 마감시";

                return CoupangJobVO.of(title, url, noticeEndDate);
            }).toList();

            jobs.addAll(scrapedJobs);
        }
        driver.quit();

        return jobs;
    }
}
