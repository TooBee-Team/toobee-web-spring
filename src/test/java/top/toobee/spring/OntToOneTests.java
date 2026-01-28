package top.toobee.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.toobee.spring.entity.ProfileEntity;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.repository.ProfileRepository;
import top.toobee.spring.repository.UserRepository;

@SpringBootTest
public class OntToOneTests {
    @Autowired UserRepository userRepository;
    @Autowired ProfileRepository profileRepository;

    @Test
    // @Transactional
    public void testC() {
        String name = RandomString.generateRandomString(6);
        final var user = new UserEntity(name, "123456");
        final var profile = new ProfileEntity(user);
        profileRepository.save(profile);
        userRepository.save(user);
    }
}
