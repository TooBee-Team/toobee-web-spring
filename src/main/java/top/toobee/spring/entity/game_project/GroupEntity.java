package top.toobee.spring.entity.game_project;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import top.toobee.spring.domain.enums.UserRole;
import top.toobee.spring.domain.model.Group;
import top.toobee.spring.entity.PlayerEntity;

@Entity
@IdClass(Group.class)
@Table(schema = "game_project", name = "group")
public class GroupEntity {
    @Id
    @JoinColumn(name = "project_id")
    @ManyToMany(fetch = FetchType.LAZY)
    public ItemEntity projectId;

    @Id
    @JoinColumn(name = "player_id")
    @ManyToMany(fetch = FetchType.LAZY)
    public PlayerEntity playerId;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column
    public UserRole role;
}
