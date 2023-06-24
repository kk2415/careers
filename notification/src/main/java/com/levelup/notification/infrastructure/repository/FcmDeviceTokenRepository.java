package com.levelup.notification.infrastructure.repository;

import com.levelup.notification.infrastructure.jpaentity.fcm.FcmDeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmDeviceTokenRepository extends JpaRepository<FcmDeviceToken, Long> {

    Optional<FcmDeviceToken> findByTopicIdAndToken(Long fcmTopicId, String token);
    Optional<FcmDeviceToken> findByToken(String token);
}
