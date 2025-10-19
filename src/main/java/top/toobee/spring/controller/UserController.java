package top.toobee.spring.controller;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import top.toobee.spring.domain.response.ChangePasswordResult;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.service.IUserService;
import top.toobee.spring.utils.DynamicTtlCache;

import java.net.InetAddress;

@RestController
@RequestMapping("/api")
public class UserController {
    private final IUserService userService;


    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private  DynamicTtlCache dynamicTtlCache;

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

    public record LoginRequest(String username, String password, String captchaVerification) {}

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(request.captchaVerification);
        ResponseModel response = captchaService.verification(captchaVO);
        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response.getRepMsg());
        }
        return userService.tryLoginAndGetToken(InetAddress.getLoopbackAddress(), request.username(), request.password());
    }

    public record UpdatePasswordRequest(String oldPassword, String newPassword) {}

    @PutMapping("change_password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, @RequestBody UpdatePasswordRequest request) {
        // TODO
        return userService.changePassword(token, request.oldPassword(), request.newPassword()).wrap();
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        dynamicTtlCache.remove(token);
        return ResponseEntity.ok().body("logout success");
    }
}

