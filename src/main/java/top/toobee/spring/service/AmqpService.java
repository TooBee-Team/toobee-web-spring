package top.toobee.spring.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AmqpService {
    private final AmqpTemplate amqpTemplate;

    @Autowired
    public AmqpService(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public Optional<Boolean> verifyPasswordFromGame(String name, String password) {
        String userInfo = name + ";" + password;
        String str = "";
        Object o = amqpTemplate.convertSendAndReceive("toobee", userInfo);
        if (o instanceof byte[]) {
            str = new String((byte[]) o);
        } else if (o != null) {
            str = o.toString();
        }
        if (str.equals("OK"))
            return Optional.of(Boolean.TRUE);
        else if(str.equals("NO"))
            return Optional.of(Boolean.FALSE);
        else
            return Optional.empty();
    }
}
