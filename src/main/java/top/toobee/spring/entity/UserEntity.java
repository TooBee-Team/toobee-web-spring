package top.toobee.spring.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

import java.util.Set;
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

    @OneToMany(mappedBy = "name", cascade = CascadeType.ALL, orphanRemoval = true) //mappedBy 关联的是 PermissionEntity 中的 name 属性
    public Set<PermissionEntity> permissions;

    public UserEntity(@Nonnull String name, @Nonnull String password) {
        this.name = name;
        this.password = password;
        this.uuid = UUID.randomUUID();
    }
    //开启级联操作，Hibernate 需要一个无参构造函数
    // 否则会报错：org.hibernate.TransientObjectException: object references an unsaved transient instance - save the transient instance before flushing
    public UserEntity() {}
}
