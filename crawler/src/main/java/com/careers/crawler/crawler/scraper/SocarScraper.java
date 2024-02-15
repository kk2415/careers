package com.careers.crawler.crawler.scraper;

import com.careers.crawler.domain.enumeration.Company;
import com.careers.crawler.domain.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
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

        List<WebElement> pagingButtons = driver.findElements(By.cssSelector("div.Pagination_Pagination__FU2nU > button.Pagination_item__GJ3ds"));
        List<Job> jobs = pagingButtons.stream()
                .map(pagingButton -> {
                    pagingButton.click();

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    List<WebElement> elements = driver.findElements(By.cssSelector("div.jobs_careerItems__ZUBkS > div.ResultItem_CareerItem__pplVI"));
                    return elements.stream()
                            .map(element -> {
                                String url = element.findElement(By.cssSelector("a")).getAttribute("href");
                                String title = element.findElement(By.cssSelector("a > div.ResultItem_info__ucxLJ > p.ResultItem_title__TW_Eq > span")).getText();
                                List<WebElement> jobGroups = element.findElements(By.cssSelector("a > span.ResultItem_tag__N9pI_"));
                                String jobGroup = jobGroups.stream().map(WebElement::getText).collect(Collectors.joining(" "));
                                String noticeEndDate = "영업 종료시";

                                if (!jobGroup.contains("개발")) {
                                    return null;
                                }

                                return Job.of(
                                        title,
                                        company,
                                        url,
                                        noticeEndDate,
                                        jobGroup
                                );
                            })
                            .filter(Objects::nonNull)
                            .toList();
                })
                .flatMap(Collection::stream)
                .filter(job -> !job.title().isEmpty() && !job.title().isBlank())
                .distinct()
                .toList();

        driver.quit();

        return jobs;
    }
}
