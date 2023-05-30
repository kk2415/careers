package com.levelup.notification.domain.service;

import com.levelup.notification.enumeration.FcmTopicName;
import com.levelup.notification.domain.service.fcm.SendMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class JobNotificationService {

    public static final String NEW_JOB_NOTIFICATION_TITLE = "신규 공고 알림";

    private final SendMessageService fcmSendMessageService;

    public void pushNewJobsNotification(FcmTopicName topicName, List<String> bodies) {
        String notificationBody = createNotificationBody(bodies);

        fcmSendMessageService.sendMessageToTopic(topicName.name(), NEW_JOB_NOTIFICATION_TITLE, notificationBody);
    }

    private String createNotificationBody(List<String> bodies) {
        //카카오 공채 공고 외 3건 신규 공고가 등록됐어요.

        if (bodies.isEmpty()) {
            return "";
        }

        if (bodies.size() == 1) {
            return bodies.get(0) + " 공고가 등록됐어요";
        }

        return bodies.get(0) + " 공고 외 " + (bodies.size() - 1) + "건 신규 공고가 등록됐어요";
    }
}
