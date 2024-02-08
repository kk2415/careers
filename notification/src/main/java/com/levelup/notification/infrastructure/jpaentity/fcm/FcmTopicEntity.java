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
@Table(name = "fcm_topic")
@Entity
public class FcmTopicEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fcm_topic_id")
    private Long id;

    private String topicName;

    public static FcmTopicEntity of(String topicName) {
        return new FcmTopicEntity(null, topicName);
    }
}
