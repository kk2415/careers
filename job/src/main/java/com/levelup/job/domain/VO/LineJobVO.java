package com.levelup.job.domain.VO;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.Company;

public class LineJobVO extends JobVO {

    private LineJobVO(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private LineJobVO(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.LINE,
                url,
                noticeEndDate);
    }

    public static LineJobVO of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new LineJobVO(title, url, noticeEndDate);
    }

    public Job toEntity() {
        return Job.of(title, company, url, noticeEndDate);
    }
}
