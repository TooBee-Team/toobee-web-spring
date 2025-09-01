package top.toobee.spring.entity.game_project;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import top.toobee.spring.domain.enums.World;
import top.toobee.spring.entity.UserEntity;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(schema = "game_project", name = "item")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, unique = true)
    public String identifier;

    @Column(nullable = false)
    public String name;

    @JoinColumn(name = "type_id")
    public TypeEntity type;

    @JoinColumn(nullable = false, name = "creator_id")
    public UserEntity creator;

    @Column(name = "webpage_created_time", nullable = false, updatable = false)
    public LocalDateTime webpageCreatedTime;

    @Column(name = "webpage_updated_time", nullable = false)
    public LocalDateTime webpageUpdatedTime;

    @Column(name = "project_created_time")
    public Date projectCreatedTime;

    @Column(name = "project_updated_time")
    public Date projectUpdatedTime;

    @Column
    public String introduction;

    @Column
    public byte[] thumbnail;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    public World world;

    @Column(nullable = false)
    public Integer x;

    @Column(nullable = false)
    public Integer y;

    @Column(nullable = false)
    public Integer z;

}
