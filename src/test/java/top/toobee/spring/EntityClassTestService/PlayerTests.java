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

import java.util.UUID;

@SpringBootTest

public class PlayerTests {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void createPlayer() {
        PlayerEntity player = new PlayerEntity();
        player.name =  RandomString.generateRandomString(6);
        player.uuid= UUID.randomUUID();
        player.white=true;
        entityManager.persist(player);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void createPlayerRole() {
        PlayerRoleEntity entity = new PlayerRoleEntity();
        entity.name= RandomString.generateRandomString(6);
        entity.description = RandomString.generateRandomString(20);
        entityManager.persist(entity);
    }
}
