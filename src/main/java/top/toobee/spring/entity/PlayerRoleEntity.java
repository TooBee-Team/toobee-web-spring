package top.toobee.spring.entity;

import jakarta.persistence.*;
import top.toobee.spring.domain.model.Role;

@Entity
@Table(schema = "public", name = "player_role")
public class PlayerRoleEntity extends Role {
}
