package com.levelup.job.domain.model;

import com.levelup.job.infrastructure.jpaentity.JobEntity;
import com.levelup.job.infrastructure.enumeration.Company;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Job {

    protected Long id;
    protected String title;
    protected Company company;
    protected String url;
    protected String noticeEndDate;
    protected String jobGroup;
    private Boolean active;
    private Boolean isPushSent;
    protected LocalDateTime createdAt;

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

    public String getSubject() {
        return "[" + company.getName() + "] " + title;
    }

    public JobEntity toEntity() {
        return JobEntity.of(null, title, company, url, noticeEndDate, jobGroup, true, false);
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
