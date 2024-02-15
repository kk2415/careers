package com.careers.notification.infrastructure.repository;

import com.careers.notification.infrastructure.jpaentity.fcm.FcmTopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FcmTopicRepository extends JpaRepository<FcmTopicEntity, Long> {
}
