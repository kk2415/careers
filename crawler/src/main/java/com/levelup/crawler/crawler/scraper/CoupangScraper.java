package com.levelup.crawler.crawler.scraper;

import com.levelup.crawler.domain.enumeration.Company;
import com.levelup.crawler.domain.model.CreateJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class CoupangScraper implements Scraper<CreateJob> {

    private final Company company = Company.COUPANG;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    @Override
    public Company getCompany() {
        return company;
    }

    @Override
    public List<CreateJob> scrape() {
        final WebDriver driver = prototypeBeanProvider.getObject();
        final String params = "search=Software+backend+frontend&location=Seoul%2C+South+Korea&location=South+Korea&pagesize=100#results";
        driver.get(company.getUrl(params));

//        ((JavascriptExecutor) driver).executeScript("location.reload()");
//        driver.get("https://www.coupang.jobs/kr/jobs?search=Software+backend+frontend&location=Seoul%2C+South+Korea&location=South+Korea&pagesize=100#results");
//        driver.navigate().refresh();

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#content > main > div.row.justify-content-between > section > div.grid.job-listing > div.card.card-job")));
        List<WebElement> elements = driver.findElements(By.cssSelector("#content > main > div.row.justify-content-between > section > div.grid.job-listing > div.card.card-job"));
        List<CreateJob> href = elements.stream()
                .map(element -> {
                    final String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
                    final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getAttribute("href");
                    final String noticeEndDate = "채용 마감시";

                    return CreateJob.of(
                            title,
                            company,
                            url,
                            noticeEndDate,
                            ""
                    );
                })
                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
                .distinct()
                .toList();
        driver.quit();
        return href;
//        final List<Integer> pages = findPages(driver, params);
//        final List<Integer> pages = List.of(1);
//
//        final List<CreateJob> jobs = pages.stream()
//                .map(page -> {
//                    driver.get(company.getUrl("page=" + page + "&" + params));
//
//                    List<WebElement> elements = driver.findElements(By.cssSelector("#content > main > div.row.justify-content-between > section > div.grid.job-listing > div.card.card-job"));
//                    return elements.stream()
//                            .map(element -> {
//                                final String title = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getText();
//                                final String url = element.findElement(By.cssSelector("div.card-body > h2.card-title > a.stretched-link")).getAttribute("href");
//                                final String noticeEndDate = "채용 마감시";
//
//                                return CreateJob.of(
//                                        title,
//                                        company,
//                                        url,
//                                        noticeEndDate,
//                                        ""
//                                );
//                            })
//                            .toList();
//                })
//                .flatMap(Collection::stream)
//                .filter(job -> !job.getTitle().isEmpty() && !job.getTitle().isBlank())
//                .distinct()
//                .toList();
//
//        driver.quit();

//        return jobs;
    }

    private List<Integer> findPages(WebDriver driver, String params) {
        driver.get(company.getUrl(params));

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
