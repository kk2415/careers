package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
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
public class LineScraper {

    private final static Company company = Company.LINE;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        String params = "ca=Engineering&ci=Bundang,Seoul";
        driver.get(company.getUrl(params));

        List<WebElement> elements = driver.findElements(By.cssSelector("ul.job_list > li"));

        ArrayList<JobVO> jobs = new ArrayList<>();
        for (WebElement element : elements) {
            try {
                String title = element.findElement(By.cssSelector("a h3.title")).getText();
                String jobNoticeUri = element.findElement(By.cssSelector("a")).getAttribute("href");
                String jobNoticePath = jobNoticeUri.substring(jobNoticeUri.lastIndexOf("/"));
                String noticeEndDate = element.findElement(By.cssSelector("a span.date")).getText();
                String url = Company.LINE.getUrl() + jobNoticePath;

                jobs.add(JobVO.of(title, company, url, noticeEndDate));
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
