package top.toobee.spring.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import top.toobee.spring.dto.User;

import java.util.List;

@Repository
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate=jdbcTemplate;
    }

    public List<User> findAll() {
        String sql = "SELECT id,name,email,password from users";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(User.class));
    }
}
