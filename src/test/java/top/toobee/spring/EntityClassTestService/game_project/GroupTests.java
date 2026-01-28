package top.toobee.spring.EntityClassTestService.game_project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.domain.enums.UserRole;
import top.toobee.spring.entity.PlayerEntity;
import top.toobee.spring.entity.game_project.GroupEntity;
import top.toobee.spring.entity.game_project.ItemEntity;

@SpringBootTest
public class GroupTests {
    @PersistenceContext private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void createGroup() {
        final var group =
                new GroupEntity(
                        entityManager.find(ItemEntity.class, 1),
                        entityManager.find(PlayerEntity.class, 4));
        group.role = UserRole.OWNER;
        entityManager.persist(group);
    }
}
