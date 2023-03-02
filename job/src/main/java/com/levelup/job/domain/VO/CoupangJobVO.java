package com.levelup.job.domain.VO;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;

public class CoupangJobVO extends JobVO {

    private CoupangJobVO(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private CoupangJobVO(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.COUPANG,
                url,
                noticeEndDate);
    }

    public static CoupangJobVO of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new CoupangJobVO(title, url, noticeEndDate);
    }

    public Job toEntity() {
        return Job.of(title, company, url, noticeEndDate);
    }
}
