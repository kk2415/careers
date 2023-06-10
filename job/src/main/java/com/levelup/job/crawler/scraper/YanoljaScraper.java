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
public class YanoljaScraper {

    private final static Company company = Company.YANOLJA;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl());

        List<String> validJobGroups = List.of("Software Engineer", "Backend Engineer", "Frontend Engineer", "DevOps Engineer", "DBA", "Infra");
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.iKWWXF > a"));
        List<JobVO> jobs = elements.stream()
                .map(element -> {
                    String title = element.findElement(By.cssSelector("li > div.eBKBVi > div.ctrzdb")).getText();
                    String url = element.getAttribute("href");
                    String noticeEndDate = "채용 마감시";
                    String jobGroup = element.findElement(By.cssSelector("li > div.eBKBVi > div.edcwkf > span.guTnnu")).getText();

                    return JobVO.of(title, company, url, noticeEndDate, jobGroup);
                })
                .filter(job -> validJobGroups.contains(job.getJobGroup()))
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .collect(Collectors.toList());

        driver.quit();

        return jobs;
    }
}
