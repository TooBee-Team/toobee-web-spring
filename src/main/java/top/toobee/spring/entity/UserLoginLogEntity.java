package top.toobee.spring.entity;

import jakarta.persistence.*;

import java.net.InetAddress;
import java.time.LocalDateTime;


@Entity
@Table(schema = "public", name = "user_login_log")
public class UserLoginLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @Column(nullable = false)
    public InetAddress ip;

    @Column(nullable = false, name = "login_time")
    public LocalDateTime loginTime;
}
