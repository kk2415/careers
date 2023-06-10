package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TossScraper {

    private final static Company company = Company.TOSS;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        String params = "?category=engineering-product&category=engineering-platform&category=core-system&category=engineering-product-platform&category=infra&category=qa&category=engineering&category=design";

        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl() + params);

        List<WebElement> aTagElements = driver.findElements(By.cssSelector("div.css-64lvsl > a[href^='/career/job-detail']"));
        List<JobVO> tossJobList = aTagElements.stream()
                .map(aTagElement -> {
                    String title = aTagElement.findElement(By.cssSelector("a.css-g65o95 > div.css-1xr69i7 > div.css-1g4e5jn.e1esrqlj0 > span.typography.typography--h5.typography--bold.color--grey700 > div > span")).getText();
                    String url = aTagElement.getAttribute("href");
                    String noticedEndedDate = "채용 마감시";

                    return JobVO.of(title, company, url, noticedEndedDate);
                }).toList();

        driver.close();
        return new ArrayList<>(tossJobList);
    }
}
