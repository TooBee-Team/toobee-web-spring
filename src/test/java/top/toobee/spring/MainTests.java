package top.toobee.spring;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainTests {

    @Autowired private RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() {}

    @Test
    void testRabbitmq() {
        String queuqeName = "toobee";
        String userInfo = "Anom;123456";
        rabbitTemplate.convertAndSend(queuqeName, userInfo);
    }
}
