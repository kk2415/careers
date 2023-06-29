package com.levelup.job.crawler.scraper;

import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.domain.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class CoupangScraper {

    public final Company company = Company.COUPANG;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        int page = 1;
        int lastPage = 5;

        List<Job> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            String params = "search=Software+engineer+backend+frontend&location=Seoul%2C+South+Korea&location=South+Korea&pagesize=20#results&page=" + page;
            driver.get(company.getUrl(params));

            List<WebElement> elements = driver.findElements(By.cssSelector("div.job-listing > div.card-job"));
            for (WebElement element : elements) {
                try {
                    final String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
                    final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getAttribute("href");
                    final String noticeEndDate = "채용 마감시";

                    jobs.add(Job.of(title, company, url, noticeEndDate));
                } catch (Exception e) {
                    log.error("{} - {}", e.getClass(), e.getMessage());
                }
            }
        }

        driver.quit();

        return jobs.stream()
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .distinct()
                .toList();
    }
}
