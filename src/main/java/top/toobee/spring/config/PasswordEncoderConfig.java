package top.toobee.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password4j.Argon2Password4jPasswordEncoder;

@Configuration
public class PasswordEncoderConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new Argon2Password4jPasswordEncoder();
    }
}
