package com.levelup.notification.domain.VO;

import com.levelup.notification.domain.entity.NotificationTemplate;
import com.levelup.notification.domain.enumeration.NotificationTemplateType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class NotificationTemplateVO {

    private Long id;
    private String title;
    private String body;

    public static NotificationTemplateVO from(NotificationTemplate template) {
        return new NotificationTemplateVO(template.getId(), template.getTitle(), template.getBody()) {
            @Override
            public boolean isSupports(NotificationTemplateType templateType) {
                return false;
            }

            @Override
            public NotificationTemplate toEntity() {
                return template;
            }
        };
    }

    public abstract boolean isSupports(NotificationTemplateType templateType);

    public abstract NotificationTemplate toEntity();
}