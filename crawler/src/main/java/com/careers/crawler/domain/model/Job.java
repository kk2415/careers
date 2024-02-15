package com.careers.crawler.domain.model;

import com.careers.crawler.domain.enumeration.Company;

import java.time.LocalDateTime;
import java.util.Objects;

public record Job(
        String title,
        Company company,
        String url,
        String noticeEndDate,
        String jobGroup,
        LocalDateTime createdAt
) {

    public static Job of(
            String title,
            Company company,
            String url,
            String noticeEndDate,
            String jobGroup
    ) {
        return new Job(
                title,
                company,
                url,
                noticeEndDate,
                jobGroup,
                LocalDateTime.now()
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Job job)) return false;

        return (title != null && title.equals(job.title)) && (url != null && url.equals(job.url));
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }
}
