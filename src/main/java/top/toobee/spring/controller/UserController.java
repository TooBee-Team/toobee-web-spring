package top.toobee.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/{id}")
    public @Nullable UserEntity get(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @PostMapping("/register")
    public @NonNull UserEntity register(@RequestParam("name") String name, @RequestParam("password") String password) {
        if (name.isBlank() || password.isEmpty())
            throw new IllegalArgumentException("name or password is empty");
        return userService.register(name, password);
    }

    @GetMapping("/create")
    public @NonNull UserEntity create() {
        return userService.createRandom();
    }
}
