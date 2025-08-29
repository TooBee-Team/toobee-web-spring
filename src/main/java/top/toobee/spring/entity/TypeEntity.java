package top.toobee.spring.entity;

import jakarta.persistence.*;

@Entity
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column
    public String name;

    @Column
    public String description;
}
