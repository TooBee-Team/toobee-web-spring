package top.toobee.spring.EntityClassTestService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.RandomString;
import top.toobee.spring.entity.PlayerEntity;

@SpringBootTest
public class PlayerTests {
    @PersistenceContext private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void createPlayer() {
        final var player = new PlayerEntity();
        player.name = RandomString.generateRandomString(6);
        player.uuid = UUID.randomUUID();
        entityManager.persist(player);
    }
}
