package top.toobee.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.service.IUserService;

import java.net.InetAddress;

@RestController
@RequestMapping("/api")
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/get/{id}")
    public @Nullable UserEntity get(@PathVariable("id") Integer id) {
        return userService.findUserById(id);
    }

    @Profile("!prod")
    @PostMapping("/user/register")
    public @NonNull UserEntity register(@RequestParam("name") String name, @RequestParam("password") String password) {
        if (name.isBlank() || password.isEmpty())
            throw new IllegalArgumentException("name or password is empty");
        return userService.register(name, password);
    }

    /*
    @Profile("!prod")
    @GetMapping("/user/create")
    public @NonNull UserEntity create() {
        return userService.createRandom();
    }
     */

    public record LoginRequest(String username, String password) {}

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userService.tryLoginAndGetToken(InetAddress.getLoopbackAddress(), request.username(), request.password());
    }

    public record UpdatePasswordRequest(String oldPassword, String newPassword) {}

    @PutMapping("change_password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody UpdatePasswordRequest request) {
        // TODO
        return null;
    }
}

