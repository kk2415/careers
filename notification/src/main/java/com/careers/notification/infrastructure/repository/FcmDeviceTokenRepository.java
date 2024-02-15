package com.careers.notification.infrastructure.repository;

import com.careers.notification.infrastructure.jpaentity.fcm.FcmDeviceTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmDeviceTokenRepository extends JpaRepository<FcmDeviceTokenEntity, Long> {

    Optional<FcmDeviceTokenEntity> findByTopicIdAndToken(Long fcmTopicId, String token);
    Optional<FcmDeviceTokenEntity> findByToken(String token);
}
