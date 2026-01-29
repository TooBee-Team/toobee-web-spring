package top.toobee.spring;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(scanBasePackages = {"top.toobee.spring", "com.anji.captcha.util"})
@RestController
public final class Main {
    static {
        System.setProperty(LoggingSystem.class.getName(), TooBeeLoggingSystem.class.getName());
    }

    static void main(String[] args) {
        final var ctx = SpringApplication.run(Main.class, args);
        final var sc = new Scanner(System.in, StandardCharsets.UTF_8);
        if (sc.nextLine().equals("stop")) {
            ctx.close();
            sc.close();
        }
    }

    @GetMapping("/")
    public String index() {
        return "Hello TooBee!";
    }
}
