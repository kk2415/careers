package com.levelup.notification.infrastructure.jpaentity.fcm;

import com.levelup.notification.infrastructure.jpaentity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "fcm_device_token")
@Entity
public class FcmDeviceToken extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_device_token_id")
    private Long id;
    private String token;

    @JoinColumn(name = "fcm_topic_id")
    @OneToOne(fetch = FetchType.LAZY)
    private FcmTopic topic;

    public static FcmDeviceToken of(String token) {
        return new FcmDeviceToken(null, token, null);
    }

    public void subscribeToTopic(FcmTopic topic) {
        this.topic = topic;
    }
}
