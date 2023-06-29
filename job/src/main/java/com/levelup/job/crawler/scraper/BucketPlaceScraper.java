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
public class BucketPlaceScraper {

    public final Company company = Company.BUCKET_PLACE;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        String params = "region=&team=dev";
        driver.get(company.getUrl(params));

        List<WebElement> elements = driver.findElements(By.cssSelector("div.recruit-page__job-list__list__wrap > a.recruit-page__job-list__list__wrap__item"));

        ArrayList<Job> jobs = new ArrayList<>();
        for (WebElement element : elements) {
            try {
                String title = element.getAccessibleName();
                String url = element.getAttribute("href");
                String noticeEndDate = "채용 마감시";

                jobs.add(Job.of(title, company, url, noticeEndDate));
            } catch (Exception e) {
                log.error("{} - {}", e.getClass(), e.getMessage());
            }
        }

        driver.quit();

        return jobs.stream()
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .toList();
    }
}
