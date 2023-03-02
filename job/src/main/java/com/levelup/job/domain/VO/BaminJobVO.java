package com.levelup.job.domain.VO;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;

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

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }
}
