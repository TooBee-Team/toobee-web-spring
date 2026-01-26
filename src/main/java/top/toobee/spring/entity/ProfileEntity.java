package top.toobee.spring.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

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
    @Column(nullable = false, updatable = false)
    public LocalDateTime ctime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_player", unique = true)
    public PlayerEntity mainPlayer;

    @Column public String email;

    @Column public String qq;

    @Column public String wechat;

    @Column public String telegram;

    @Column public String nickname;

    @Column public String introduction;

    public ProfileEntity() {}

    public ProfileEntity(UserEntity user) {
        this.user = user;
    }
}
