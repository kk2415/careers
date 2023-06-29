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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class YanoljaScraper {

    public final Company company = Company.YANOLJA;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl());

        List<String> validJobGroups = List.of("Software Engineer", "Backend Engineer", "Frontend Engineer", "DevOps Engineer", "DBA", "Infra");
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.iKWWXF > a"));

        ArrayList<Job> jobs = new ArrayList<>();
        for (WebElement element : elements) {
            try {
                String title = element.findElement(By.cssSelector("li > div.eBKBVi > div.ctrzdb")).getText();
                String url = element.getAttribute("href");
                String noticeEndDate = "채용 마감시";
                String jobGroup = element.findElement(By.cssSelector("li > div.eBKBVi > div.edcwkf > span.guTnnu")).getText();

                jobs.add(Job.of(title, company, url, noticeEndDate, jobGroup));
            } catch (Exception e) {
                log.error("{} - {}", e.getClass(), e.getMessage());
            }
        }

        driver.quit();

        return jobs.stream()
                .filter(job -> validJobGroups.contains(job.getJobGroup()))
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .collect(Collectors.toList());
    }
}
