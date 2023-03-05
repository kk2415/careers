package com.levelup.job.domain.vo;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;

public class NaverJobVO extends JobVO {

    private NaverJobVO(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private NaverJobVO(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.NAVER,
                url,
                noticeEndDate);
    }

    public static NaverJobVO of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new NaverJobVO(title, url, noticeEndDate);
    }

    public Job toEntity() {
        return Job.of(title, company, url, noticeEndDate);
    }
}
