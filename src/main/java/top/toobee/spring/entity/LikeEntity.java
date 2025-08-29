package top.toobee.spring.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
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
