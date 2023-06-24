package com.levelup.job.crawler.scraper;

import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.domain.vo.JobVO;
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
public class NaverScraper {

    private final static Company company = Company.NAVER;
    private final ObjectProvider<WebDriver> prototypeBeanProvider;

    public List<JobVO> scrape() {
        WebDriver driver = prototypeBeanProvider.getObject();

        String params = "subJobCdArr=1010001%2C1010002%2C1010003%2C1010004%2C1010005%2C1010006%2C1010007%2C1010008%2C1010020%2C1020001%2C1030001%2C1030002%2C1040001%2C1060001&sysCompanyCdArr=KR%2CNB%2CWM%2CSN%2CNL%2CWTKR%2CNFN%2CNI&empTypeCdArr=&entTypeCdArr=&workAreaCdArr=&sw=&subJobCdData=1010001&subJobCdData=1010002&subJobCdData=1010003&subJobCdData=1010004&subJobCdData=1010005&subJobCdData=1010006&subJobCdData=1010007&subJobCdData=1010008&subJobCdData=1010020&subJobCdData=1020001&subJobCdData=1030001&subJobCdData=1030002&subJobCdData=1040001&subJobCdData=1060001&sysCompanyCdData=KR&sysCompanyCdData=NB&sysCompanyCdData=WM&sysCompanyCdData=SN&sysCompanyCdData=NL&sysCompanyCdData=WTKR&sysCompanyCdData=NFN&sysCompanyCdData=NI";
        driver.get(company.getUrl(params));

        List<WebElement> elements = scrollToEnd(driver);

        ArrayList<JobVO> jobs = new ArrayList<>();
        for (WebElement element : elements) {
            try {
                String title = element.findElement(By.cssSelector("a.card_link > h4.card_title")).getText();

                String htmlOnClickAttrValue = element.findElement(By.cssSelector("a.card_link")).getAttribute("onclick");
                String jobNoticeKey;
                if (htmlOnClickAttrValue.contains("'")) {
                    jobNoticeKey = htmlOnClickAttrValue.substring(htmlOnClickAttrValue.indexOf("'") + 1, htmlOnClickAttrValue.lastIndexOf("'"));
                } else {
                    jobNoticeKey = htmlOnClickAttrValue.substring(htmlOnClickAttrValue.indexOf("(") + 1, htmlOnClickAttrValue.lastIndexOf(")"));
                }

                String url = "https://recruit.navercorp.com/rcrt/view.do?annoId=" + jobNoticeKey;
                String noticeEndDate = element.findElement(By.cssSelector("dl.card_info dd.info_text:last-child")).getText();

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

    private List<WebElement> scrollToEnd(WebDriver driver) {
        List<WebElement> elements = driver.findElements(By.cssSelector("ul.card_list > li"));
        int prevSize = elements.size();

        for (int i = 0; i < 100; i++) {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

            // Wait for more items to load
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            elements = driver.findElements(By.cssSelector("ul.card_list > li"));
            int curSize = elements.size();

            if (prevSize == curSize) {
                break;
            }
            prevSize = curSize;
        }

        return elements;
    }
}
