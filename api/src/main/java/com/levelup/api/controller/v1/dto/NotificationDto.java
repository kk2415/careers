package com.levelup.api.controller.v1.dto;

import java.util.List;

public class NotificationDto {

    public record NotificationRequest(
            List<String> notificationBodies
    ) {}
}
