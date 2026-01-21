package top.toobee.spring.entity;

import jakarta.persistence.*;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

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

    public UserEntity(@NonNull String name, @NonNull String password) {
        this.name = name;
        this.password = password;
        this.roleId = 1;
    }

    public UserEntity() {}
}
