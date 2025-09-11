package top.toobee.spring.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "name", cascade = CascadeType.ALL, orphanRemoval = true) //mappedBy 关联的是 PermissionEntity 中的 name 属性
    public Set<PermissionEntity> permissions;

    public UserEntity(@Nonnull String name, @Nonnull String password) {
        this.name = name;
        this.password = password;
    }

    // 开启级联操作，Hibernate 需要一个无参构造函数
    // 否则会报错：org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing
    public UserEntity() {}

    public Set<GrantedAuthority> getAuthorities() {
        return permissions == null ? Collections.emptySet() :
                permissions.stream().map(PermissionEntity::getAuthority).collect(Collectors.toUnmodifiableSet());
    }

    public UserDetails toUserDetails() {
        return new User(name, password, getAuthorities());
    }
}
