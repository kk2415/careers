package com.levelup.crawler.config;

import com.levelup.crawler.scheduler.CrawlingScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 스프링 컨테이너 초기화 직후 실행됨
 * 필요에 따라 ContextStartedEvent 대신 상황에 맞게 다른 이벤트를 넣어줄 수도 있다
 * */
@Slf4j
@RequiredArgsConstructor
@Component
public class SpringApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final CrawlingScheduler crawlingScheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
//            crawlingScheduler.crawlingJobs();
        } catch (Exception e) {
            log.error("SpringApplicationListener exception 발생, message: {}", e.getMessage());
        }
    }
}
