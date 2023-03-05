package com.levelup.api.controller.v1.notification;

import com.levelup.notification.domain.vo.FcmDeviceTokenVO;
import com.levelup.notification.domain.service.fcm.DeviceTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "FCM 디바이스 토큰 API")
@RequiredArgsConstructor
@RequestMapping("/api/v1/fcm/device-token")
@RestController
public class FcmDeviceTokenController {

    private final DeviceTokenService fcmService;

    @Operation(summary = "FCM 디바이스 토큰 DB에 저장")
    @PostMapping
    public ResponseEntity<FcmDeviceTokenVO> createDeviceToken(@RequestParam String token) {
        FcmDeviceTokenVO fcmDeviceTokenVO = fcmService.saveFcmDeviceToken(token);

        return ResponseEntity.status(HttpStatus.CREATED).body(fcmDeviceTokenVO);
    }
}
