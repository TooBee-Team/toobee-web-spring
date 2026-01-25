package top.toobee.spring.entity;

import jakarta.persistence.*;
import java.util.UUID;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.jspecify.annotations.NonNull;

@Entity
@Table(schema = "public", name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, unique = true, insertable = false, updatable = false)
    @Generated(event = EventType.INSERT)
    public UUID uuid;

    @Column(nullable = false, unique = true)
    public String name;

    @Column(nullable = false)
    public String password;

    @Column(name = "role_id", nullable = false)
    public Integer roleId;

    @Column(nullable = false)
    public Boolean locked;

    public UserEntity(@NonNull String name, @NonNull String password) {
        this.name = name;
        this.password = password;
        this.roleId = 1;
        this.locked = false;
    }

    public UserEntity() {}
}
