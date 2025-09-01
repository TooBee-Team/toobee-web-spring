package top.toobee.spring;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import top.toobee.spring.entity.ProfileEntity;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.repository.ProfileRepository;
import top.toobee.spring.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Random;

@SpringBootTest
public class OntToOneTests {

    private static final Logger logger = LoggerFactory.getLogger(OntToOneTests.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProfileRepository profileRepository;
    @Test
    //@Transactional
    public void testC() {
        String name = generateRandomString(6);
        UserEntity user = new UserEntity(name, "123456");
        // 记录日志信息

        ProfileEntity profile = new ProfileEntity();
        profile.user=user;
        profileRepository.save(profile);
        userRepository.save(user);
    }

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        SecureRandom rnd = new SecureRandom();

        while (sb.length() < length) {
            // Generate a random number between 0 (inclusive) and 36 (exclusive)
            int randomChar = rnd.nextInt(36);
            // Convert the number to a character
            char c = (char) (randomChar < 26 ? 'a' + randomChar : '0' + randomChar - 26);
            sb.append(c);
        }
        return sb.toString();
    }

}

