package top.toobee.spring.entity;

import jakarta.persistence.*;
import java.net.InetAddress;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLInetJdbcType;

@Entity
@Table(schema = "public", name = "user_login_log")
public class UserLoginLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @JdbcType(PostgreSQLInetJdbcType.class)
    @Column(nullable = false, columnDefinition = "inet")
    public InetAddress ip;

    @Column(nullable = false, name = "login_time")
    public LocalDateTime loginTime;

    public UserLoginLogEntity() {}

    public UserLoginLogEntity(InetAddress ip, UserEntity user) {
        this.user = user;
        this.ip = ip;
        this.loginTime = LocalDateTime.now();
    }
}
