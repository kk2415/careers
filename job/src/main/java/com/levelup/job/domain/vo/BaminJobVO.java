package com.levelup.job.domain.vo;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;

public class BaminJobVO extends JobVO {

    private BaminJobVO(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private BaminJobVO(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.BAMIN,
                url,
                noticeEndDate);
    }

    public static BaminJobVO of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new BaminJobVO(title, url, noticeEndDate);
    }

    public Job toEntity() {
        return Job.of(title, company, url, noticeEndDate);
    }
}
