package top.toobee.spring.entity;

import jakarta.persistence.*;
import top.toobee.spring.domain.model.UserPerm;

import java.time.LocalDateTime;

@Entity
@IdClass(UserPerm.class)
@Table(schema = "public", name = "user_perm")
public class UserPermEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perm_id")
    public UserPermEntity perm;

    @Column(name = "created_time", nullable = false, updatable = false)
    public LocalDateTime createdTime;
}
