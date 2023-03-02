package com.levelup.job.domain.VO;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;

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

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }
}
