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
public class LineScraper {

    private final static Company company = Company.LINE;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        String params = "?ca=Engineering&ci=Bundang,Seoul";

        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl() + params);

        List<WebElement> elements = driver.findElements(By.cssSelector("ul.job_list > li"));
        List<JobVO> jobs = elements.stream().map(element -> {
            String title = element.findElement(By.cssSelector("a h3.title")).getText();

            String jobNoticeUri = element.findElement(By.cssSelector("a")).getAttribute("href");
            String jobNoticePath = jobNoticeUri.substring(jobNoticeUri.lastIndexOf("/"));
            String noticeEndDate = element.findElement(By.cssSelector("a span.date")).getText();
            String url = Company.LINE.getUrl() + jobNoticePath;

            return JobVO.of(title, company, url, noticeEndDate);
        }).collect(Collectors.toList());

        driver.quit();

        return jobs;
    }
}
