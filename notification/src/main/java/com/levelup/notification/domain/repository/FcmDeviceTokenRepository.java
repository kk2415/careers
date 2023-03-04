package com.levelup.notification.domain.repository;

import com.levelup.notification.domain.entity.fcm.FcmDeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FcmDeviceTokenRepository extends JpaRepository<FcmDeviceToken, Long> {

    Optional<FcmDeviceToken> findByTopicIdAndToken(Long fcmTopicId, String token);
    Optional<FcmDeviceToken> findByToken(String token);
}
