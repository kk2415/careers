package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.model.Job;
import com.levelup.job.infrastructure.enumeration.Company;
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
public class SocarScraper implements Scraper<Job> {

    private final Company company = Company.SOCAR;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl());

        List<Job> jobs = new ArrayList<>();
        List<WebElement> buttons = driver.findElements(By.cssSelector("div.Pagination_Pagination__FU2nU > button.Pagination_item__GJ3ds"));
        buttons.forEach(button -> {
            button.click();

            try {
                Thread.sleep(1000); // 3초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<WebElement> elements = driver.findElements(By.cssSelector("div.jobs_careerItems__ZUBkS > div.ResultItem_CareerItem__pplVI"));
            for (WebElement element : elements) {
                try {
                    String url = element.findElement(By.cssSelector("a")).getAttribute("href");
                    String title = element.findElement(By.cssSelector("a > div.ResultItem_info__ucxLJ > p.ResultItem_title__TW_Eq > span")).getText();
                    List<WebElement> jobGroups = element.findElements(By.cssSelector("a > span.ResultItem_tag__N9pI_"));
                    String jobGroup = jobGroups.stream().map(WebElement::getText).collect(Collectors.joining(" "));
                    String noticeEndDate = "영업 종료시";

                    if (jobGroup.contains("개발")) {
                        jobs.add(Job.of(title, company, url, noticeEndDate, jobGroup));
                    }
                } catch (Exception e) {
                    log.error("{} - {}", e.getClass(), e.getMessage());
                }
            }
        });

        driver.quit();

        return jobs.stream()
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .distinct()
                .toList();
    }
}
