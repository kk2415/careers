package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BaminScraper {

    private final static Company company = Company.BAMIN;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl());

        List<WebElement> jobElements = scrollToEnd(driver);
        List<JobVO> jobs = jobElements.stream()
                .map(jobElement -> {
                    String title = jobElement.findElement(By.cssSelector("div.flag-btn > div.share-group")).getAccessibleName();
                    String url = jobElement.findElement(By.cssSelector("a.title")).getAttribute("href");
                    String noticeEndDate = "영업 종료시";

                    driver.get(url);
                    WebElement element = driver.findElement(By.cssSelector("div.fr-view"));
                    System.out.println(element);

                    return JobVO.of(title, company, url, noticeEndDate);
                })
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .collect(Collectors.toList());

        driver.quit();

        return jobs;
    }

    private List<WebElement> scrollToEnd(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.cssSelector("div.recruit-list > ul.recruit-type-list li:not(.loading-observer)"));
        int prevSize = elements.size();

        for (int i = 0; i < 100; i++) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

            try {
                Thread.sleep(1000); // Wait for more items to load
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            elements = driver.findElements(By.cssSelector("div.recruit-list > ul.recruit-type-list li:not(.loading-observer)"));
            int curSize = elements.size();

            // 스크롤을 끝까지 내리고 조회한 리스트 길이가 예전 리스트와 같다면 스크롤 끝에 도달했다는 의미
            if (prevSize == curSize) {
                break;
            }
            prevSize = curSize;
        }

        return elements;
    }
}
