package top.toobee.spring.domain.model;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(updatable = false, nullable = false, unique = true)
    protected String name;

    @Column
    public String description;

    public Role() {}

    public String getName() {
        return name;
    }

    public abstract void setName(String name);
}
