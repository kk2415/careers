package com.levelup.crawler.crawler.scraper;

import com.levelup.crawler.domain.enumeration.Company;
import com.levelup.crawler.domain.model.CreateJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Component
public class KakaoScraper implements Scraper<CreateJob> {

    private final Company company = Company.KAKAO;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<CreateJob> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        try {
            final int firstPage = 1;
            final int lastPage = 6;

            return IntStream.rangeClosed(firstPage, lastPage)
                    .mapToObj(page -> {
                        String params = "skilset=Android,iOS,Windows,Web_front,DB,Cloud,Server,Hadoop_eco_system,Algorithm_Ranking,System&company=ALL&page=" + page;
                        driver.get(company.getUrl(params));

                        List<WebElement> elements = driver.findElements(By.cssSelector("ul.list_jobs a"));
                        return elements.stream()
                                .map(element -> {
                                    String title = element.findElement(By.cssSelector("h4.tit_jobs")).getText();
                                    final String url = element.getAttribute("href");
                                    final String noticeEndDate = element.findElement(By.cssSelector("dl.list_info > dd")).getText();

                                    return CreateJob.of(
                                            title,
                                            company,
                                            url,
                                            noticeEndDate,
                                            ""
                                    );
                                })
                                .toList();
                    })
                    .flatMap(Collection::stream)
                    .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                    .distinct()
                    .toList();
        } finally {
            driver.quit();
        }
    }
}
