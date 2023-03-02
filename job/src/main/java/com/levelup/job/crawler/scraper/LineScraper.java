package com.levelup.job.crawler.scraper;

import com.levelup.job.crawler.jsoupConnetion.JsoupConnectionMaker;
import com.levelup.job.domain.VO.JobVO;
import com.levelup.job.domain.VO.LineJobVO;
import com.levelup.job.domain.enumeration.Company;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LineScraper {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    private JsoupTemplate jsoupTemplate;

    public LineScraper(@Qualifier("LineConnectionMaker") JsoupConnectionMaker connectionMaker) {
        jsoupTemplate = JsoupTemplate.from(connectionMaker);
    }

    public List<JobVO> findJobs() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        WebDriver driver = new ChromeDriver();

        String params = "?ca=Engineering&ci=Bundang,Seoul";
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
