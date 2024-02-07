package com.levelup.job.controller.v1.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public class NotificationDto {

    public record NotificationRequest(
            @NotNull
            List<String> notificationBodies
    ) {}
}
