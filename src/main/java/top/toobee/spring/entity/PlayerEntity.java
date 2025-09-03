package top.toobee.spring.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.UUID;

@Entity
@Table(schema = "public", name = "player")
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(unique = true, updatable = false, nullable = false)
    public UUID uuid;

    @Column(unique = true, updatable = false, nullable = false)
    public String name;

    @Column(nullable = false)
    public Boolean white = false;

    @Column(nullable = false)
    public Boolean fake = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    public PlayerRoleEntity playerRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    public UserEntity user;
}
