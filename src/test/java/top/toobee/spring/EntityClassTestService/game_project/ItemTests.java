package top.toobee.spring.EntityClassTestService.game_project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.RandomString;
import top.toobee.spring.domain.enums.World;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.entity.game_project.ItemEntity;

import java.time.LocalDateTime;

@SpringBootTest
public class ItemTests {
    @PersistenceContext
    private EntityManager entityManager;
    @Test
    @Transactional
    @Rollback(false)
    public void createItem(){
        ItemEntity item = new ItemEntity();
        item.identifier = RandomString.generateRandomString(15);
        item.name = RandomString.generateRandomString(15);
        item.creator = entityManager.find(UserEntity.class,4);
        item.webpageUpdatedTime = LocalDateTime.now();
        item.world = World.OVERWORLD;
        item.x = 5;
        item.y = 5;
        item.z = 5;
        entityManager.persist(item);
    }
}
