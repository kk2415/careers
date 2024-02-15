package com.careers.crawler.crawler.scraper;

import com.careers.crawler.domain.enumeration.Company;
import com.careers.crawler.domain.model.Job;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TossScraper implements Scraper<Job> {

    private final Company company = Company.TOSS;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        String params = "category=engineering-product&category=engineering-platform&category=core-system&category=engineering-product-platform&category=qa&category=engineering&category=design&category=data&category=infra&category=security&category=infra-security";
        driver.get(company.getUrl(params));

        List<WebElement> aTagElements = driver.findElements(By.cssSelector("div.css-16ht878 > a.css-g65o95"));
        List<Job> jobs = aTagElements.stream()
                .map(aTagElement -> {
                    String title = aTagElement.findElement(By.cssSelector("div.css-1xr69i7 > div > span.typography.typography--h5.typography--bold.color--grey700 > div > span.typography.typography--h5.typography--bold.color--grey700")).getText();
                    String url = aTagElement.getAttribute("href");
                    String noticedEndedDate = "채용 마감시";
                    String jobGroup = aTagElement.findElement(By.cssSelector("div.css-1xr69i7 > div > span.typography.typography--p.typography--regular.color--grey700")).getText();

                    return Job.of(
                            title,
                            company,
                            url,
                            noticedEndedDate,
                            jobGroup
                    );
                })
                .filter(job -> !job.title().isEmpty() && !job.title().isBlank())
                .distinct()
                .toList();

        driver.quit();

        return jobs;
    }
}
