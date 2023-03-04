package com.levelup.notification.domain.VO.notificationTemplate;

import com.levelup.notification.domain.entity.NotificationTemplate;
import com.levelup.notification.domain.enumeration.NotificationTemplateType;

public class NewApplicantNotificationTemplateVO extends NotificationTemplateVO {

    private NewApplicantNotificationTemplateVO(Long id, String title, String body) {
        super(id, title, body);
    }

    public static NewApplicantNotificationTemplateVO of(Long id, String title, String body) {
        return new NewApplicantNotificationTemplateVO(id, title, body);
    }

    public static NotificationTemplateVO of(String applicantName, String channelName) {
        return NewApplicantNotificationTemplateVO.of(null, createTitle(), createBody(applicantName, channelName));
    }

    public NotificationTemplate toEntity() {
        return NotificationTemplate.of(this.getId(), this.getTitle(), this.getBody());
    }

    private static String createTitle() {
        return "누군가 나의 스터디/프로젝트에 지원 했어요!";
    }

    private static String createBody(String applicantName, String channelName) {
        return applicantName + "님이 " + channelName + "에 가입을 신청하였습니다.";
    }

    public boolean isSupports(NotificationTemplateType templateType) {
        return NotificationTemplateType.NEW_JOB.equals(templateType);
    }
}
