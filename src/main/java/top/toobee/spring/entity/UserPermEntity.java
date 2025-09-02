package top.toobee.spring.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import top.toobee.spring.domain.model.UserPerm;

import java.time.LocalDateTime;

@Entity
@Table(schema = "public", name = "user_perm")
public class UserPermEntity {
    @EmbeddedId
    public UserPerm id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    public UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("permId")
    @JoinColumn(name = "perm_id", nullable = false, insertable = false, updatable = false)
    public PermissionEntity perm;

    @Column(name = "created_time", nullable = false, updatable = false)
    @CreationTimestamp
    public LocalDateTime createdTime;

    public UserPermEntity() {}

    public UserPermEntity(UserEntity user, PermissionEntity perm) {
        this.id = new UserPerm(user.id, perm.id);
        this.user = user;
        this.perm = perm;
    }
}
