package com.levelup.job.domain.vo;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;

public class TossJobVO extends JobVO {

    private TossJobVO(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private TossJobVO(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.TOSS,
                url,
                noticeEndDate);
    }

    public static TossJobVO of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new TossJobVO(title, url, noticeEndDate);
    }

    public Job toEntity() {
        return Job.of(title, company, url, noticeEndDate);
    }
}
