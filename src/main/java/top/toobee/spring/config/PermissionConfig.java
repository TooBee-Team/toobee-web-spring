package top.toobee.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.toobee.spring.repository.PermissionRepository;
import top.toobee.spring.service.PermissionContainer;

@Configuration
public class PermissionConfig {
    @Bean
    public PermissionContainer permissionContainer(PermissionRepository permissionRepository) {
        return new PermissionContainer(permissionRepository);
    }
}
