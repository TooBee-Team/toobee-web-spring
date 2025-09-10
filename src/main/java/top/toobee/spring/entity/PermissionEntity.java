package top.toobee.spring.entity;

import jakarta.persistence.*;
import top.toobee.spring.domain.model.Role;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(schema = "public", name = "permission")
public class PermissionEntity extends Role {

}
