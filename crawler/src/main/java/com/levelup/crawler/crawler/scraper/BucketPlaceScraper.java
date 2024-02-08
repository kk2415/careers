package com.levelup.crawler.crawler.scraper;

import com.levelup.crawler.domain.enumeration.Company;
import com.levelup.crawler.domain.model.CreateJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class BucketPlaceScraper implements Scraper<CreateJob> {

    private final Company company = Company.BUCKET_PLACE;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<CreateJob> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        String params = "region=&team=dev";
        driver.get(company.getUrl(params));

        List<WebElement> elements = driver.findElements(By.cssSelector("div.recruit-page__job-list__list__wrap > a.recruit-page__job-list__list__wrap__item"));
        List<CreateJob> jobs = elements.stream()
                .map(element -> {
                    String title = element.getAccessibleName();
                    String url = element.getAttribute("href");
                    String noticeEndDate = "채용 마감시";
                    return CreateJob.of(
                            title,
                            company,
                            url,
                            noticeEndDate,
                            ""
                    );
                })
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .toList();

        driver.quit();

        return jobs;
    }
}
