package top.toobee.spring.entity;

import jakarta.persistence.*;
import top.toobee.spring.domain.enums.World;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(schema = "public", name = "item")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false, unique = true)
    public String identifier;

    @Column(nullable = false)
    public String name;

    @JoinColumn(name = "type_id")
    public TypeEntity type;

    @JoinColumn(nullable = false,name = "creator_id")
    public UserEntity creator;

    @Column(name = "webpage_created_time",nullable = false)
    public LocalDateTime webpageCreatedTime;

    @Column(name = "webpage_updated_time",nullable = false)
    public LocalDateTime webpageUpdatedTime;

    @Column(name = "project_created_time")
    public Date projectCreatedTime;

    @Column(name = "project_updated_time")
    public Date projectUpdatedTime;

    @Column
    public String introduction;

    @Column
    public byte[] thumbnail;

    @Column(nullable = false)
    public World world;

    @Column(nullable = false)
    public Integer x;

    @Column(nullable = false)
    public Integer y;

    @Column(nullable = false)
    public Integer z;

}
