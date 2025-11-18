package top.toobee.spring.entity.game_project;

import jakarta.persistence.*;

@Entity
@Table(schema = "game_project", name = "type")
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column public String name;

    @Column public String description;
}
