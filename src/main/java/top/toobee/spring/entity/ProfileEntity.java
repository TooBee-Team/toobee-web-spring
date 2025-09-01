package top.toobee.spring.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ProfileEntity {
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @Column(name = "created_time", nullable = false, updatable = false)
    public LocalDateTime createdTime;

    @Column(name = "last_login_time")
    public LocalDateTime lastLoginTime;

    @Column
    public String email;

    @Column
    public String qq;

    @Column
    public String wechat;

    @Column
    public String telegram;

    @Column
    public String nickname;

    @Column
    public String introduction;
}
