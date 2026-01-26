package top.toobee.spring.entity.game_project;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import top.toobee.spring.entity.UserEntity;

@Entity
@Table(schema = "game_project", name = "like")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public UserEntity user;

    @JoinColumn(name = "project_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    public ItemEntity project;

    @Column(nullable = false)
    @CreationTimestamp
    public LocalDateTime ctime;
}
