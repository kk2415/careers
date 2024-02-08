package com.levelup.crawler.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SeleniumWebDriverConfig {

    @Value("${webdriver.chrome.driver}")
    private String chromeDriver;

    @Bean
    @Scope("prototype")
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", chromeDriver);

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("--remote-allow-origins=*");

        return new ChromeDriver(chromeOptions);
    }
}
