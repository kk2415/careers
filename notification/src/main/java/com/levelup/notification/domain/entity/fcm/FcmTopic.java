package com.levelup.notification.domain.entity.fcm;

import com.levelup.notification.domain.entity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "fcm_topic")
@Entity
public class FcmTopic extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_topic_id")
    private Long id;

    private String topicName;

    public static FcmTopic of(String topicName) {
        return new FcmTopic(null, topicName);
    }
}
