package top.toobee.spring;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.toobee.spring.entity.ProfileEntity;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.repository.ProfileRepository;
import top.toobee.spring.repository.UserRepository;

@SpringBootTest
public class OntToOneTests {

    private static final Logger logger = LoggerFactory.getLogger(OntToOneTests.class);
    @Autowired UserRepository userRepository;
    @Autowired ProfileRepository profileRepository;

    @Test
    // @Transactional
    public void testC() {
        String name = RandomString.generateRandomString(6);
        UserEntity user = new UserEntity(name, "123456");
        // 记录日志信息

        ProfileEntity profile = new ProfileEntity();
        profile.user = user;
        profileRepository.save(profile);
        userRepository.save(user);
    }
}
