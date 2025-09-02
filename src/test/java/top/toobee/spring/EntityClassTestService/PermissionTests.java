package top.toobee.spring.EntityClassTestService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.RandomString;
import top.toobee.spring.domain.model.UserPerm;
import top.toobee.spring.entity.PermissionEntity;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.entity.UserPermEntity;

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
            permission.name = user.name;
            permission.description = RandomString.generateRandomString(15);
            entityManager.persist(permission);
            UserPermEntity userPermEntity = new UserPermEntity(user, permission);
            entityManager.persist(userPermEntity);

        }
        else {
            throw new RuntimeException("用户不存在");
        }

    }
}
