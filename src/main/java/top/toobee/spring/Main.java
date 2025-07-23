package top.toobee.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Scanner;

//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
@RestController

public final class Main {
	static {
		System.setProperty(LoggingSystem.class.getName(), TooBeeLoggingSystem.class.getName());
	}

	public static void main(String[] args) {
		final var ctx = SpringApplication.run(Main.class, args);
		final var sc = new Scanner(System.in);
		if (sc.nextLine().equals("stop")) {
			ctx.close();
			sc.close();
		}
	}

	@GetMapping("/")
	public String index(Principal principal) {
		if (principal == null) {
			return "Hello TooBee!";
		}
		return "欢迎登录:"+principal.getName()+"!";
	}

}
