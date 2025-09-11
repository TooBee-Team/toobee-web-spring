package top.toobee.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import top.toobee.spring.domain.model.Role;

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

    public GrantedAuthority getAuthority() {
        return authority;
    }
}
