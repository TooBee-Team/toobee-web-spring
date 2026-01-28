package top.toobee.spring.EntityClassTestService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import top.toobee.spring.RandomString;
import top.toobee.spring.entity.ProfileEntity;
import top.toobee.spring.entity.UserEntity;

@SpringBootTest
public class UserTests {
    @PersistenceContext private EntityManager entityManager;

    @Test
    @Transactional
    @Rollback(false)
    public void createUser() {
        final var user = new UserEntity(RandomString.generateRandomString(6), "123456");
        final var profile = new ProfileEntity(user);
        profile.email =
                RandomString.generateRandomString(6)
                        + "@"
                        + RandomString.generateRandomString(6)
                        + ".com";
        profile.wechat = RandomString.generateRandomString(13);
        entityManager.persist(profile);
        entityManager.persist(user);
    }
}
