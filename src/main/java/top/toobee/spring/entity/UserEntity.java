package top.toobee.spring.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(schema = "public", name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, unique = true, updatable = false)
    public UUID uuid;

    @Column(nullable = false, unique = true)
    public String name;

    @Column(nullable = false)
    public String password;

    public UserEntity(@Nonnull String name, @Nonnull String password) {
        this.name = name;
        this.password = password;
        this.uuid = UUID.randomUUID();
    }
}
