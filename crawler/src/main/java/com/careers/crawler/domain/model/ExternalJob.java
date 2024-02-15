package com.careers.crawler.domain.model;

import com.careers.crawler.domain.enumeration.Company;

import java.time.LocalDateTime;
import java.util.List;

public class ExternalJob {

    public record Requests(
            List<Request> jobs
    ) {
        public static Requests from(List<Request> jobs) {
            return new Requests(jobs);
        }
    }

    public record Request(
            String title,
            Company company,
            String url,
            String noticeEndDate,
            String jobGroup
    ) {
        public static Request of(
                String title,
                Company company,
                String url,
                String noticeEndDate,
                String jobGroup
        ) {
            return new Request(
                    title,
                    company,
                    url,
                    noticeEndDate,
                    jobGroup
            );
        }
    }

    public record Response(
            Long id,
            String title,
            Company company,
            String url,
            String noticeEndDate,
            LocalDateTime createdAt
    ) {
    }
}
