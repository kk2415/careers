package com.levelup.job.web.api.dto;

import java.util.List;

public class NotificationDto {

    public record NotificationRequest(
            List<String> notificationBodies
    ) {
        public static NotificationRequest from(List<String> notificationBodies) {
            return new NotificationRequest(notificationBodies);
        }
    }
}
