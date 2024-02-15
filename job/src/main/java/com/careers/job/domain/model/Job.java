package com.careers.job.domain.model;

import com.careers.job.infrastructure.jpaentity.JobEntity;
import com.careers.job.infrastructure.enumeration.Company;

import java.time.LocalDateTime;
import java.util.Objects;

public record Job(
        Long id,
        String title,
        Company company,
        String url,
        String noticeEndDate,
        String jobGroup,
        Boolean active,
        Boolean isPushSent,
        LocalDateTime createdAt
) {

    public static Job of(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        return new Job(
                null,
                title,
                company,
                url,
                noticeEndDate,
                "",
                true,
                false,
                LocalDateTime.now()
        );
    }

    public static Job of(
            String title,
            Company company,
            String url,
            String noticeEndDate,
            String jobGroup
    ) {
        return new Job(
                null,
                title.trim(),
                company,
                url.trim(),
                noticeEndDate,
                jobGroup,
                true,
                false,
                LocalDateTime.now()
        );
    }

    public static Job from(JobEntity job) {
        return new Job(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getUrl(),
                job.getNoticeEndDate(),
                job.getJobGroup(),
                job.getActive(),
                job.getIsPushSent(),
                job.getCreatedAt()
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
