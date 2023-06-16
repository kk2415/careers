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
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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

    protected JobVO(Long id, String title, Company company, String url, String noticeEndDate) {
        this.id = id;
        this.title = title.trim();
        this.company = company;
        this.url = url.trim();
        this.noticeEndDate = noticeEndDate.trim();
        this.jobGroup = "";
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    protected JobVO(Long id, String title, Company company, String url, String noticeEndDate, String jobGroup) {
        this.id = id;
        this.title = title.trim();
        this.company = company;
        this.url = url.trim();
        this.noticeEndDate = noticeEndDate.trim();
        this.jobGroup = jobGroup;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

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
                noticeEndDate
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
                jobGroup
        );
    }

    public static JobVO from(Job job) {
        return new JobVO(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getUrl(),
                job.getNoticeEndDate(),
                ""
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
