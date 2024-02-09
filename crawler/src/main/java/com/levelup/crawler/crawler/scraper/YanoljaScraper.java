package com.levelup.crawler.crawler.scraper;

import com.levelup.crawler.domain.enumeration.Company;
import com.levelup.crawler.domain.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class YanoljaScraper implements Scraper<Job> {

    private final Company company = Company.YANOLJA;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl());

        List<String> validJobGroups = List.of("Software Engineer", "Backend Engineer", "Frontend Engineer", "DevOps Engineer", "DBA", "Infra");
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.iKWWXF > a"));

        List<Job> jobs = elements.stream()
                .map(element -> {
                    String title = element.findElement(By.cssSelector("li.gBlYBh > div.eBKBVi > div.hsclxU")).getText();
                    String url = element.getAttribute("href");
                    String noticeEndDate = "채용 마감시";
                    String jobGroup = element.findElement(By.cssSelector("li.gBlYBh > div.eBKBVi > div.hSIEYY > span.gDzMae")).getText();

                    return Job.of(
                            title,
                            company,
                            url,
                            noticeEndDate,
                            jobGroup
                    );
                })
                .filter(job -> validJobGroups.contains(job.jobGroup()))
                .filter(job -> !job.title().isEmpty() && !job.title().isBlank())
                .collect(Collectors.toList());

        driver.quit();

        return jobs;
    }
}
