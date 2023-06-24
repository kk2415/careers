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
public class CarrotMarketScraper {

    private final static Company company = Company.CARROT_MARKET;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl());

        List<WebElement> elements = driver.findElements(By.cssSelector("ul.c-jpGEAj > div"));

        ArrayList<Job> jobs = new ArrayList<>();
        for (WebElement element : elements) {
            try {
                String title = element.findElement(By.cssSelector("li.c-deAcZv > a.c-hCDnza > div.c-MPFyP > h3.c-boyXyq")).getAccessibleName();
                String url = element.findElement(By.cssSelector("li.c-deAcZv > a.c-hCDnza")).getAttribute("href");
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
