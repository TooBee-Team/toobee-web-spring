package top.toobee.spring.EntityClassTestService.game_project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.entity.PlayerEntity;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.entity.game_project.ItemEntity;
import top.toobee.spring.entity.game_project.LikeEntity;

@SpringBootTest
public class LikeTests {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void createLike(){
        LikeEntity like = new LikeEntity();
        like.project = entityManager.find(ItemEntity.class,1);
        like.user = entityManager.find(UserEntity.class,4);
        entityManager.persist(like);
    }
}
