package top.toobee.spring.entity.game_project;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import top.toobee.spring.domain.enums.UserRole;
import top.toobee.spring.domain.model.Group;
import top.toobee.spring.entity.PlayerEntity;

@Entity
@Table(schema = "game_project", name = "group")
public class GroupEntity {
    @EmbeddedId
    public Group id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("projectId")
    @JoinColumn(name = "project_id", nullable = false, insertable = false, updatable = false)
    public ItemEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playerId")
    @JoinColumn(name = "player_id", nullable = false, insertable = false, updatable = false)
    public PlayerEntity player;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    public UserRole role;
}
