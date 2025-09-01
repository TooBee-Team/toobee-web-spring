package top.toobee.spring.domain.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
@MappedSuperclass
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column
    public String name;

    @Column
    public String description;

    public Role() {
    }
}
