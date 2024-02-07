package com.levelup.crawler.crawler.scraper;

import com.levelup.job.domain.model.CreateJob;
import com.levelup.job.infrastructure.enumeration.Company;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TossScraper implements Scraper<CreateJob> {

    private final Company company = Company.TOSS;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<CreateJob> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        String params = "category=engineering-product&category=engineering-platform&category=core-system&category=engineering-product-platform&category=qa&category=engineering&category=design&category=data&category=infra&category=security&category=infra-security";
        driver.get(company.getUrl(params));

        List<WebElement> aTagElements = driver.findElements(By.cssSelector("div.css-64lvsl > a[href^='/career/job-detail']"));
        List<CreateJob> jobs = aTagElements.stream()
                .map(aTagElement -> {
                    String title = aTagElement.findElement(By.cssSelector("a.css-g65o95 > div.css-1xr69i7 > div.css-1g4e5jn.e1esrqlj0 > span.typography.typography--h5.typography--bold.color--grey700 > div > span")).getText();
                    String url = aTagElement.getAttribute("href");
                    String noticedEndedDate = "채용 마감시";

                    return CreateJob.of(
                            title,
                            company,
                            url,
                            noticedEndedDate,
                            ""
                    );
                })
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .distinct()
                .toList();

        driver.quit();

        return jobs;
    }
}
