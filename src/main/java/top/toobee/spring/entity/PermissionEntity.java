package top.toobee.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.toobee.spring.domain.model.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "public", name = "permission")
public class PermissionEntity extends Role {
    @Transient
    private GrantedAuthority authority;

    @Override
    public void setName(String name) {
        this.name = name;
        this.authority = new SimpleGrantedAuthority(name);
    }

    /*权限和用户是多对多的关系，通过中间表 permission_user 关联
    * 多对多的关系两张表都需要维护关联关系
    * 这里的 mappedBy 关联的是 UserEntity 中的 permissions 属性
    * */
    @ManyToMany(mappedBy = "permissions")
    public Set<UserEntity> users;

    public GrantedAuthority getAuthority() {
        return authority;
    }
}
