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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_perm", // 指定连接表的名称为 "user_perm
            // 定义当前实体（UserEntity）在连接表中的外键列，名为 "user_id"，引用 "id" 列
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            // 定义关联实体（PermissionEntity）在连接表中的外键列，名为 "perm_id"，引用 "id" 列
            inverseJoinColumns = @JoinColumn(name = "perm_id", referencedColumnName = "id")
    )
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
