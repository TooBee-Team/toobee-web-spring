package top.toobee.spring.EntityClassTestService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.RandomString;
import top.toobee.spring.entity.PlayerEntity;
import top.toobee.spring.entity.PlayerRoleEntity;

@SpringBootTest
public class PlayerRoleTests {
    @PersistenceContext private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void createPlayerRole() {
        PlayerEntity player = entityManager.find(PlayerEntity.class, 5);
        if (player != null) {
            PlayerRoleEntity playerRole = new PlayerRoleEntity();
            playerRole.setName(player.name);
            playerRole.description = RandomString.generateRandomString(15);
            entityManager.persist(playerRole);
            player.playerRole = playerRole;
            entityManager.merge(player);
        } else throw new RuntimeException("玩家不存在");
    }
}
