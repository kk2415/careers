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
public class CoupangScraper {

    private final static Company company = Company.COUPANG;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        int page = 1;
        int lastPage = 5;
        String params;

        WebDriver driver = prototypeBeanProvider.getObject();
        List<JobVO> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            params = "?search=Software+engineer+backend+frontend&location=Seoul%2C+South+Korea&location=South+Korea&pagesize=20#results&page=" + page;
            driver.get(company.getUrl() + params);

            List<WebElement> elements = driver.findElements(By.cssSelector("div.job-listing > div.card-job"));
            List<JobVO> scrapedJobs = elements.stream().map((element) -> {
                final String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
                final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getAttribute("href");
                final String noticeEndDate = "채용 마감시";

                return JobVO.of(title, company, url, noticeEndDate);
            }).toList();

            jobs.addAll(scrapedJobs);
        }
        driver.quit();

        return jobs;
    }
}
