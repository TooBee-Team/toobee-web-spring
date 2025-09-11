package top.toobee.spring.EntityClassTestService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.RandomString;
import top.toobee.spring.domain.model.UserPerm;
import top.toobee.spring.entity.PermissionEntity;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.entity.UserPermEntity;

import java.util.Set;
import java.util.UUID;

@SpringBootTest
public class PermissionTests {
    @PersistenceContext
    private EntityManager entityManager;
    @Test
    @Transactional
    @Rollback(false)
    public void createPermission() {
        PermissionEntity permission = new PermissionEntity();
        UserEntity user = entityManager.find(UserEntity.class, 15);
        if (user != null){
            permission.setName(user.name);
            permission.description = RandomString.generateRandomString(15);
            entityManager.persist(permission);
            UserPermEntity userPermEntity = new UserPermEntity(user, permission);
            entityManager.persist(userPermEntity);

        }
        else {
            throw new RuntimeException("用户不存在");
        }

    }

    @Test
    @Transactional
    public void testPermission() {
        PermissionEntity permission = new PermissionEntity();
        permission.setName("ROLE_ADMIN");
        permission.description="管理员权限";
        GrantedAuthority authority = permission.getAuthority();
        if (authority.getAuthority().equals( "ROLE_ADMIN")){
            System.out.println("权限正确");
        }
        else {
            System.out.println("权限错误");
        }
        UserEntity user = entityManager.find(UserEntity.class, 22);
        Set<PermissionEntity> permissions = user.permissions;
        for (PermissionEntity p : permissions){
            System.out.println(p.getName());
        }
    }
}
