package top.toobee.spring.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(schema = "public", name = "user_profile")
public class ProfileEntity {
    @Id
    @Column(name = "user_id")
    public Integer userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @CreationTimestamp
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
