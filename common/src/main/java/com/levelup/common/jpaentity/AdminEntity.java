package com.levelup.common.jpaentity;

import com.levelup.common.jpaentity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "admin")
@Entity
public class AdminEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    private String username;
    private String password;

    public static AdminEntity of(String username, String password) {
        return new AdminEntity(null, username, password);
    }
}
