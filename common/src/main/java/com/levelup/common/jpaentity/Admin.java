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
public class Admin extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    private String username;
    private String password;

    public static Admin of(String username, String password) {
        return new Admin(null, username, password);
    }
}
