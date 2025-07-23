package top.toobee.spring.config;

import jakarta.annotation.PostConstruct;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import top.toobee.spring.handler.UUIDTypeHandler;

import java.util.UUID;

@org.springframework.context.annotation.Configuration
public class MyBatisConfig {

    private final org.apache.ibatis.session.SqlSessionFactory sqlSessionFactory;

    public MyBatisConfig(org.apache.ibatis.session.SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @PostConstruct
    public void addTypeHandlers() {
        Configuration configuration = sqlSessionFactory.getConfiguration();
        TypeHandlerRegistry registry = configuration.getTypeHandlerRegistry();
        registry.register(UUID.class, new UUIDTypeHandler());
    }
}