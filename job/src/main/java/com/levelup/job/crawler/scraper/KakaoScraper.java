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
public class KakaoScraper {

    private final static Company company = Company.KAKAO;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        int page = 1;
        int lastPage = 5;
        String params;

        WebDriver driver = prototypeBeanProvider.getObject();
        List<JobVO> jobs = new ArrayList<>();
        for (; page <= lastPage; ++page) {
            params = "skilset=Android,iOS,Windows,Web_front,DB,Cloud,Server,Hadoop_eco_system,Algorithm_Ranking,System&company=ALL&page=" + page;
            driver.get(company.getUrl(params));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            List<WebElement> elements = driver.findElements(By.cssSelector("ul.list_jobs a"));
            for (WebElement element : elements) {
                try {
                    String title = element.findElement(By.cssSelector("h4.tit_jobs")).getText();
                    final String url = element.getAttribute("href");
                    final String noticeEndDate = element.findElement(By.cssSelector("dl.list_info > dd")).getText();

                    jobs.add(JobVO.of(title, company, url, noticeEndDate));
                } catch (Exception e) {
                    log.error("{} - {}", e.getClass(), e.getMessage());
                }
            }
        }

        driver.quit();

        return jobs.stream()
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .toList();
    }
}
