package com.levelup.job.crawler.scraper;

import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.domain.model.Job;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class CoupangScraper implements Scraper<Job> {

    private final Company company = Company.COUPANG;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<Job> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();
        driver.get(company.getUrl("search=Software+engineer+backend+frontend&location=Seoul%2C+South+Korea&location=South+Korea&pagesize=20#results"));

        List<Integer> pages = findPages(driver);
        List<Job> jobs = pages.stream()
                .map(page -> {
                    String params = "page=" + page + "&search=Software+engineer+backend+frontend&location=Seoul%2C+South+Korea&location=South+Korea&pagesize=20#results";
                    driver.get(company.getUrl(params));

                    List<WebElement> elements = driver.findElements(By.cssSelector("div.job-listing > div.card-job"));
                    return elements.stream()
                            .map(element -> {
                                final String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
                                final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getAttribute("href");
                                final String noticeEndDate = "채용 마감시";

                                return Job.of(title, company, url, noticeEndDate);
                            })
                            .toList();
                })
                .flatMap(Collection::stream)
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .distinct()
                .toList();

        driver.quit();

        return jobs;
    }

    private List<Integer> findPages(WebDriver driver) {
        return driver.findElements(By.cssSelector("ul.pagination > li.page-item")).stream()
                .map(WebElement::getText)
                .map(text -> {
                    try {
                        return Integer.parseInt(text);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
