package com.careers.notification.domain.service.fcm;

import com.careers.notification.domain.model.FcmDeviceToken;
import com.careers.notification.infrastructure.jpaentity.fcm.FcmDeviceTokenEntity;
import com.careers.notification.infrastructure.repository.FcmDeviceTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceTokenService {

    private final FcmDeviceTokenRepository fcmDeviceTokenRepository;

    @Transactional
    public FcmDeviceToken saveFcmDeviceToken(String token) {
        Optional<FcmDeviceTokenEntity> optionalToken = fcmDeviceTokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            return FcmDeviceToken.from(optionalToken.get());
        }

        FcmDeviceTokenEntity savedDeviceToken = fcmDeviceTokenRepository.save(FcmDeviceTokenEntity.of(token));

        return FcmDeviceToken.from(savedDeviceToken);
    }
}
