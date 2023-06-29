package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.model.Job;
import com.levelup.job.infrastructure.enumeration.Company;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class SKScraper {

    private final static Company company = Company.SK;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl());
        List<WebElement> elements = scrollToEnd(driver);
        ArrayList<Job> jobs = new ArrayList<>();
        for (WebElement element : elements) {
            try {
                String company = element.findElement(By.cssSelector("div.info-area > a > div.company")).getText();
                WebElement aTag = element.findElement(By.cssSelector("div.subject-area > a"));
                String subjectArea = aTag.getText();
                String title = getTitle(company, subjectArea);
                String jobGroup = getJobGroup(subjectArea);
                String url = aTag.getAttribute("href");
                String noticeEndDate = "영업 종료시";

                if (jobGroup.contains("개발") && !jobGroup.contains("사업개발")) {
                    jobs.add(Job.of(title, SKScraper.company, url, noticeEndDate, jobGroup));
                }
            } catch (Exception e) {
                log.error("{} - {}", e.getClass(), e.getMessage());
            }
        }

        driver.quit();

        return jobs;
    }

    private List<WebElement> scrollToEnd(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.cssSelector("div#RecruitList > div.recruit-list-item"));
        int prevSize = elements.size();

        for (int i = 0; i < 100; i++) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

            try {
                Thread.sleep(1000); // Wait for more items to load
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            elements = driver.findElements(By.cssSelector("div#RecruitList > div.recruit-list-item"));
            int curSize = elements.size();

            // 스크롤을 끝까지 내리고 조회한 리스트 길이가 예전 리스트와 같다면 스크롤 끝에 도달했다는 의미
            if (prevSize == curSize) {
                break;
            }
            prevSize = curSize;
        }

        return elements;
    }

    private String getTitle(String company, String subjectArea) {
        String[] splitSubjectArea = subjectArea.split("\n");
        if (splitSubjectArea.length < 1) {
            return "";
        }

        String title = splitSubjectArea[0];
        if (title.contains("[")) {
            int startIndex = title.indexOf("[");
            int endIndex = title.indexOf("]");

            if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
                return title;
            }
        }

        return "[" + company + "] " + title;
    }

    private String getJobGroup(String subjectArea) {
        String[] splitSubjectArea = subjectArea.split("\n");
        if (splitSubjectArea.length < 2) {
            return "";
        }

        return splitSubjectArea[1];
    }
}
