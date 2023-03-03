package com.levelup.notification.domain.VO;

import com.levelup.notification.domain.entity.NotificationTemplate;
import com.levelup.notification.domain.enumeration.NotificationTemplateType;

public class JobNotificationTemplateVO extends NotificationTemplateVO {

    private JobNotificationTemplateVO(Long id, String title, String body) {
        super(id, title, body);
    }

    public static JobNotificationTemplateVO of(Long id, String title, String body) {
        return new JobNotificationTemplateVO(id, title, body);
    }

    public static NotificationTemplate from(String body) {
        return NotificationTemplate.of(null, createTitle(), createBody(body));
    }

    public NotificationTemplate toEntity() {
        return NotificationTemplate.of(this.getId(), this.getTitle(), this.getBody());
    }

    private static String createTitle() {
        return "새로운 공고가 올라왔습니다!";
    }

    private static String createBody(String body) {
        return body;
    }

    public boolean isSupports(NotificationTemplateType templateType) {
        return NotificationTemplateType.NEW_JOB.equals(templateType);
    }
}
