package com.levelup.job.domain.VO;

import com.levelup.job.domain.entity.Job;
import com.levelup.job.domain.enumeration.ClosingType;
import com.levelup.job.domain.enumeration.Company;

public class KakaoJobVO extends JobVO {

    /*카카오 공고는 상시 채용일 경우 마김 일자가 "영입종료시" 으로 되어있다.*/
    private static String INFINITE_CLOSING_TYPE = "영입종료시";

    private KakaoJobVO(
            String title,
            Company company,
            String url,
            String noticeEndDate
    ) {
        super(null, title, company, url, noticeEndDate);
    }

    private KakaoJobVO(
            String title,
            String url,
            String noticeEndDate
    ) {
        super(null,
                title,
                Company.KAKAO,
                url,
                noticeEndDate);
    }

    public static KakaoJobVO of(
            String title,
            String url,
            String noticeEndDate
    ) {
        return new KakaoJobVO(title, url, noticeEndDate);
    }

    public Job toEntity() {
        return Job.of(title, company, url, noticeEndDate);
    }

    /**
     * 채용 공고 마감 방식이 상시 채용인지 마감 기한이 있는지 체크
     * */
    public ClosingType matchClosingType(String closingDate) {
        if (closingDate.equals(INFINITE_CLOSING_TYPE)) {
            return ClosingType.INFINITE;
        }
        return ClosingType.DEAD_LINE;
    }
}
