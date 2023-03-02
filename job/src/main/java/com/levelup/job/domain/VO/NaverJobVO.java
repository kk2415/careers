package com.levelup.job.domain.VO;

import com.levelup.recruit.domain.entity.JobEntity;
import com.levelup.recruit.domain.enumeration.Company;

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

    public JobEntity toEntity() {
        return JobEntity.of(title, company, url, noticeEndDate);
    }
}
