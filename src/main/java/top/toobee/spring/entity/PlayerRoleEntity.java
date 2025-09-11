package top.toobee.spring.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import top.toobee.spring.domain.model.Role;

@Entity
@Table(schema = "public", name = "player_role")
public class PlayerRoleEntity extends Role {
    @Override
    public void setName(String name) {
        this.name = name;
    }
}
