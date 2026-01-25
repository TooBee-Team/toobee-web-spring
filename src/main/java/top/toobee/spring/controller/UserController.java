package top.toobee.spring.controller;

import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import java.net.InetAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import top.toobee.spring.result.LoginResult;
import top.toobee.spring.service.BanIpService;
import top.toobee.spring.service.IUserService;
import top.toobee.spring.utils.DynamicTtlCache;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final CaptchaService captchaService;
    private final BanIpService banIpService;
    private final DynamicTtlCache dynamicTtlCache;

    @Autowired
    public UserController(
            IUserService userService,
            CaptchaService captchaService,
            BanIpService banIpService,
            DynamicTtlCache dynamicTtlCache) {
        this.userService = userService;
        this.captchaService = captchaService;
        this.banIpService = banIpService;
        this.dynamicTtlCache = dynamicTtlCache;
    }

    public record LoginRequest(String username, String password, String captchaVerification) {}

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // TODO: 解析客户端的真实IP
        final var ip = InetAddress.getLoopbackAddress();
        final var banTime = banIpService.banTime(ip, request.username());
        if (banTime > 0) return new LoginResult.IpBanned(banTime).to();
        final var captchaVO = new CaptchaVO();
        captchaVO.setCaptchaVerification(request.captchaVerification);
        final var response = captchaService.verification(captchaVO);
        return response.isSuccess()
                ? userService.loginAndGetToken(ip, request.username(), request.password()).to()
                : LoginResult.CaptchaError.fromResponseModel(response).to();
    }

    public record UpdatePasswordRequest(String oldPassword, String newPassword) {}

    @PutMapping("change_password")
    public ResponseEntity<?> changePassword(
            Authentication auth, @RequestBody UpdatePasswordRequest request) {
        return userService
                .changePassword(auth.getName(), request.oldPassword(), request.newPassword())
                .to();
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout(
            @RequestHeader("Authorization") String token, Authentication auth) {
        dynamicTtlCache.removeToken(auth.getName(), token);
        // TODO: log
        return ResponseEntity.ok().build();
    }
}
