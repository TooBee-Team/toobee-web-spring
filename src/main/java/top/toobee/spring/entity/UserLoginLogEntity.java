package top.toobee.spring.entity;

import jakarta.persistence.*;
import java.net.InetAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;
import org.hibernate.dialect.type.PostgreSQLInetJdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.toobee.spring.domain.enums.LoginStatus;

@Entity
@Table(schema = "public", name = "user_login_log")
public class UserLoginLogEntity {
    private static final Logger logger = LoggerFactory.getLogger(UserLoginLogEntity.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity user;

    @JdbcType(PostgreSQLInetJdbcType.class)
    @Column(nullable = false, columnDefinition = "inet")
    private String ip;

    @Column(nullable = false, name = "login_time")
    public LocalDateTime loginTime;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false, name = "login_status")
    public LoginStatus loginStatus;

    public UserLoginLogEntity() {}

    public UserLoginLogEntity(InetAddress ip, UserEntity user, LoginStatus loginStatus) {
        this.user = user;
        this.ip = ip.getHostAddress();
        this.loginStatus = loginStatus;
        this.loginTime = LocalDateTime.now(ZoneId.systemDefault());
    }

    public InetAddress getIp() {
        try {
            return ip == null ? InetAddress.getLoopbackAddress() : InetAddress.getByName(ip);
        } catch (Exception e) {
            logger.error("Error while parsing IP address: {}", ip, e);
            return InetAddress.getLoopbackAddress();
        }
    }

    public void setIp(InetAddress ip) {
        this.ip = ip.getHostAddress();
    }
}
