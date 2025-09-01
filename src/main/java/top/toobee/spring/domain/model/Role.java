package top.toobee.spring.domain.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@MappedSuperclass
public abstract class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, unique = true)
    public String name;

    @Column
    public String description;

    public Role() {
    }
}
