package com.levelup.notification.domain.service.fcm;

import com.levelup.notification.domain.vo.FcmDeviceTokenVO;
import com.levelup.notification.infrastructure.jpaentity.fcm.FcmDeviceTokenEntity;
import com.levelup.notification.infrastructure.repository.FcmDeviceTokenRepository;
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
    public FcmDeviceTokenVO saveFcmDeviceToken(String token) {
        Optional<FcmDeviceTokenEntity> optionalToken = fcmDeviceTokenRepository.findByToken(token);
        if (optionalToken.isPresent()) {
            return FcmDeviceTokenVO.from(optionalToken.get());
        }

        FcmDeviceTokenEntity savedDeviceToken = fcmDeviceTokenRepository.save(FcmDeviceTokenEntity.of(token));

        return FcmDeviceTokenVO.from(savedDeviceToken);
    }
}
