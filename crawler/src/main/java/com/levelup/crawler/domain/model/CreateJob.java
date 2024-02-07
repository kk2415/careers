package com.levelup.crawler.domain.model;

import com.levelup.job.infrastructure.enumeration.Company;
import com.levelup.job.infrastructure.jpaentity.JobEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateJob {

    protected String title;
    protected Company company;
    protected String url;
    protected String noticeEndDate;
    protected String jobGroup;
    private Boolean active;
    private Boolean isPushSent;
    protected LocalDateTime createdAt;

    public static CreateJob of(
            String title,
            Company company,
            String url,
            String noticeEndDate,
            String jobGroup
    ) {
        return new CreateJob(
                title,
                company,
                url,
                noticeEndDate,
                jobGroup,
                Boolean.TRUE,
                Boolean.FALSE,
                LocalDateTime.now()
        );
    }

    public JobEntity toEntity() {
        return JobEntity.of(
                null,
                title,
                company,
                url,
                noticeEndDate,
                jobGroup,
                active,
                isPushSent
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateJob job)) return false;

        return (title != null && title.equals(job.title)) && (url != null && url.equals(job.url));
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, url);
    }
}
