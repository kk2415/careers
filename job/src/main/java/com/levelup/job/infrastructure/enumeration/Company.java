package com.levelup.job.infrastructure.enumeration;

public enum Company {

    NAVER("네이버", "https://recruit.navercorp.com/rcrt/list.do"),
    KAKAO("카카오", "https://careers.kakao.com/jobs"),
    LINE("라인", "https://careers.linecorp.com/ko/jobs"),
    COUPANG("쿠팡", "https://www.coupang.jobs/kr/jobs"),
    BAMIN("우아한형제", "https://career.woowahan.com/?keyword=&category=jobGroupCodes%3ABA005001#recruit-list"),
    TOSS("토스", "https://toss.im/career/jobs"),
    CARROT_MARKET("당근마켓", "https://team.daangn.com/jobs/"),
    BUCKET_PLACE("오늘의 집", "https://www.bucketplace.com/careers/"),
    YANOLJA("야놀자", "https://careers.yanolja.co"),
    SK("SK", "https://thecareers.sktelecom.com/Recruit"),
    SOCAR("쏘카", "https://www.socarcorp.kr/careers/jobs"),
    OTHER("그 외", "url"),
    ;

    /*채용 공고 사이트 주소*/
    private final String name;
    private final String url;

    Company(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getUrl(String param) {
        return url + "?" + param;
    }
}
