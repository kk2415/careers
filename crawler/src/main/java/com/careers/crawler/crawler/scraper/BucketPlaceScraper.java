package com.careers.crawler.crawler.scraper;

import com.careers.crawler.domain.enumeration.Company;
import com.careers.crawler.domain.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BucketPlaceScraper implements Scraper<Job> {

    private final Company company = Company.BUCKET_PLACE;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        String params = "region=&team=dev";
        driver.get(company.getUrl(params));

        List<WebElement> elements = driver.findElements(By.cssSelector("div.recruit-page__job-list__list__wrap > a.recruit-page__job-list__list__wrap__item"));
        List<Job> jobs = elements.stream()
                .map(element -> {
                    String title = element.getAccessibleName();
                    String url = element.getAttribute("href");
                    String noticeEndDate = "채용 마감시";
                    return Job.of(
                            title,
                            company,
                            url,
                            noticeEndDate,
                            ""
                    );
                })
                .filter(job -> !job.title().isEmpty() && !job.title().isBlank())
                .toList();

        driver.quit();

        return jobs;
    }
}
