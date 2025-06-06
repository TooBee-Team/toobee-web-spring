package top.toobee.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.toobee.spring.dto.User;
import top.toobee.spring.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/pg/searchAll")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
