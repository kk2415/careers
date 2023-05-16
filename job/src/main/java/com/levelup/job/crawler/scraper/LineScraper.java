package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.vo.LineJobVO;
import com.levelup.job.domain.enumeration.Company;
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

    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> findJobs() {
        String params = "?ca=Engineering&ci=Bundang,Seoul";

        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(Company.LINE.getUrl() + params);

        List<WebElement> elements = driver.findElements(By.cssSelector("ul.job_list > li"));
        List<JobVO> jobs = elements.stream().map(element -> {
            String title = element.findElement(By.cssSelector("a h3.title")).getText();

            String jobNoticeUri = element.findElement(By.cssSelector("a")).getAttribute("href");
            String jobNoticePath = jobNoticeUri.substring(jobNoticeUri.lastIndexOf("/"));
            String url = Company.LINE.getUrl() + jobNoticePath;

            String noticeEndDate = element.findElement(By.cssSelector("a span.date")).getText();
            return LineJobVO.of(title, url, noticeEndDate);
        }).collect(Collectors.toList());

        driver.quit();

        return jobs;
    }
}
