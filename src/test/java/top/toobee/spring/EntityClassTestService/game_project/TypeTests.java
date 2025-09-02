package top.toobee.spring.EntityClassTestService.game_project;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.RandomString;
import top.toobee.spring.entity.game_project.TypeEntity;

@SpringBootTest
public class TypeTests {
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void createType(){
        TypeEntity type = new TypeEntity();
        type.description = RandomString.generateRandomString(15);
        type.name = RandomString.generateRandomString(15);
        entityManager.persist(type);
    }
}
