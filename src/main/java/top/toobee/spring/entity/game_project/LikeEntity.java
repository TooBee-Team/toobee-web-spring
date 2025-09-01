package top.toobee.spring.entity.game_project;

import jakarta.persistence.*;
import top.toobee.spring.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(schema = "game_project", name = "like")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    public UserEntity user;

    @JoinColumn(name = "project_id", nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    public ItemEntity project;

    @Column(name = "created_time", nullable = false)
    public LocalDateTime createdTime;
}
