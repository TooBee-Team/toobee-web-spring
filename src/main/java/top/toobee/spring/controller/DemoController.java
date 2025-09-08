package top.toobee.spring.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Profile("dev")
@RestController
@RequestMapping("/api/demo")
public class DemoController {
    @GetMapping("/me")
    public String me(Principal principal) {
        return "当前用户:" + principal.getName();
    }
}
