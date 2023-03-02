package com.levelup.job.domain.VO;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;
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

    public static JobVO from(JobEntity jobEntity) {
        return new JobVO(
                jobEntity.getId(),
                jobEntity.getTitle(),
                jobEntity.getCompany(),
                jobEntity.getUrl(),
                jobEntity.getNoticeEndDate(),
                jobEntity.getCreatedAt());
    }

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
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
