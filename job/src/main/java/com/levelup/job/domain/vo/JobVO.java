package com.levelup.job.domain.vo;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JobVO {

    protected Long id;
    protected String title;
    protected Company company;
    protected String url;
    protected String noticeEndDate;
    protected String jobGroup;
    private Boolean active;
    protected LocalDateTime createdAt;

    public static JobVO of(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        return new JobVO(
                null,
                title,
                company,
                url,
                noticeEndDate,
                "",
                true,
                LocalDateTime.now()
        );
    }

    public static JobVO of(
            String title,
            Company company,
            String url,
            String noticeEndDate,
            String jobGroup
    ) {
        return new JobVO(
                null,
                title,
                company,
                url,
                noticeEndDate,
                jobGroup,
                true,
                LocalDateTime.now()
        );
    }

    public static JobVO from(Job job) {
        return new JobVO(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getUrl(),
                job.getNoticeEndDate(),
                job.getJobGroup(),
                job.getActive(),
                job.getCreatedAt()
        );
    }

    public String getSubject() {
        return "[" + company.getName() + "] " + title;
    }

    public Job toEntity() {
        return Job.of(title, company, url, noticeEndDate, jobGroup);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobVO jobVO)) return false;

        return (company != null && company.equals(jobVO.company)) && (url != null && url.equals(jobVO.url));
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, url);
    }
}
