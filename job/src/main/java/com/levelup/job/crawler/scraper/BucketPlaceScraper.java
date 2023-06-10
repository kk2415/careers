package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BucketPlaceScraper {

    private final static Company company = Company.BUCKET_PLACE;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        String params = "region=&team=dev";

        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl(params));

        List<WebElement> elements = driver.findElements(By.cssSelector("div.recruit-page__job-list__list__wrap > a.recruit-page__job-list__list__wrap__item"));
        List<JobVO> jobs = elements.stream().map(element -> {
            String title = element.getText();
            String url = element.getAttribute("href");
            String noticeEndDate = "채용 마감시";

            return JobVO.of(title, company, url, noticeEndDate);
        }).collect(Collectors.toList());

        driver.quit();

        return jobs;
    }
}
