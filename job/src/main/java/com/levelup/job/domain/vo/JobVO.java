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
    protected LocalDateTime createdAt;

    public JobVO(Long id, String title, Company company, String url, String noticeEndDate) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.url = url;
        this.noticeEndDate = noticeEndDate;
        this.createdAt = LocalDateTime.now();
    }

    public static JobVO of(
            String title,
            Company company,
            String url,
            String noticeEndDate,
            LocalDateTime created)
    {
        return new JobVO(null, title, company, url, noticeEndDate, created);
    }

    public static JobVO from(Job job) {
        return new JobVO(
                job.getId(),
                job.getTitle(),
                job.getCompany(),
                job.getUrl(),
                job.getNoticeEndDate(),
                job.getCreatedAt());
    }

    public Job toEntity() {
        return Job.of(title, company, url, noticeEndDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobVO)) return false;
        JobVO jobVO = (JobVO) o;

        return (company != null && company.equals(jobVO.company)) && (url != null && url.equals(jobVO.url));
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, url);
    }
}
