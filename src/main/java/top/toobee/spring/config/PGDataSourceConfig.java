package top.toobee.spring.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class PGDataSourceConfig {
    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Bean
    DataSource dataSource() {
        final var config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setAutoCommit(true);
        config.setConnectionTimeout(5000);
        return new HikariDataSource(config);
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(@Autowired DataSource dataSource) {
        final var props = new Properties();
        props.setProperty("hibernate.show_sql", "true");
        props.setProperty("hibernate.format_sql", "true");
        props.setProperty("hibernate.highlight_sql", "true");
        final var emFactory = new LocalContainerEntityManagerFactoryBean();
        emFactory.setJpaProperties(props);
        emFactory.setDataSource(dataSource);
        emFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emFactory.setPackagesToScan("top.toobee.spring.entity");
        return emFactory;
    }
}
