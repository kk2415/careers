package com.levelup.job.domain.entity;

import com.levelup.job.domain.entity.base.BaseTimeEntity;
import com.levelup.job.domain.enumeration.Company;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "job")
@Entity
public class Job extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "job_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Company company;

    private String noticeEndDate;

    public static Job of(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        return new Job(null, title, url, company, noticeEndDate);
    }

    public void update(String title, String url, Company company, String noticeEndDate) {
        this.title = title;
        this.url = url;
        this.company = company;
        this.noticeEndDate = noticeEndDate;
    }
}
