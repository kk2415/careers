package com.levelup.job.crawler.scraper;

import com.levelup.job.domain.vo.JobVO;
import com.levelup.job.domain.vo.TossJobVO;
import com.levelup.job.domain.enumeration.Company;
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

    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> findJobs() {
        String params = "?category=engineering-product&category=engineering-platform&category=core-system&category=engineering-product-platform&category=infra&category=qa&category=engineering&category=design";

        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(Company.TOSS.getUrl() + params);

        List<WebElement> aTagElements = driver.findElements(By.cssSelector("div.css-64lvsl > a.css-g65o95"));
        List<TossJobVO> tossJobList = aTagElements.stream()
                .map(aTagElement -> {
                    String title = aTagElement.findElement(By.cssSelector("span.typography.typography--h5.typography--bold.color--grey700.css-4bd2r9")).getText();
                    String url = aTagElement.getAttribute("href");
                    String noticedEndedDate = "채용 마감시";

                    return TossJobVO.of(title, url, noticedEndedDate);
                }).toList();

        driver.close();
        return new ArrayList<>(tossJobList);
    }
}
