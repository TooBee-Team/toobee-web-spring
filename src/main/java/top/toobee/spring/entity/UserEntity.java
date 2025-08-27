package top.toobee.spring.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "public", name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, unique = true, updatable = false)
    public UUID uuid;

    @Column(nullable = false, unique = true)
    public String name;

    @Column(nullable = false)
    public String password;

    @Column(name = "created_time", nullable = false, updatable = false)
    public LocalDateTime createdTime;

    @Column(name = "last_login_time")
    public LocalDateTime lastLoginTime;

    public String email;

    public String qq;

    public String wechat;

    public String telegram;

    public String nickname;

    public String introduction;

    public UserEntity(@Nonnull String name, @Nonnull String password) {
        this.name = name;
        this.password = password;
        this.uuid = UUID.randomUUID();
        this.createdTime = LocalDateTime.now();
    }
}
