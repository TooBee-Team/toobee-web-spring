package top.toobee.spring.entity;

import jakarta.persistence.*;
import top.toobee.spring.domain.enums.UserRole;
import top.toobee.spring.domain.model.Group;
@Entity
@IdClass(Group.class)
@Table(schema = "public", name = "group")
public class GroupEntity {
    @Id
    @JoinColumn(name = "project_id")
    @ManyToMany(fetch = FetchType.LAZY)
    public ItemEntity projectId;

    @Id
    @JoinColumn(name = "player_id")
    @ManyToMany(fetch = FetchType.LAZY)
    public PlayerEntity playerId;

    @Column
    public UserRole role;
}
