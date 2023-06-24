package com.levelup.notification.infrastructure.repository;

import com.levelup.notification.infrastructure.jpaentity.fcm.FcmTopic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTopicRepository extends JpaRepository<FcmTopic, Long> {
}
