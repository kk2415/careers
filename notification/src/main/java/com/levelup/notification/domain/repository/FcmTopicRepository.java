package com.levelup.notification.domain.repository;

import com.levelup.notification.domain.entity.fcm.FcmTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTopicRepository extends JpaRepository<FcmTopic, Long> {
}
