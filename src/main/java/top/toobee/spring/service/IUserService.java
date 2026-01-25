package top.toobee.spring.service;

import java.net.InetAddress;
import org.springframework.security.core.userdetails.UserDetailsService;
import tools.jackson.databind.JsonNode;
import top.toobee.spring.controller.dto.RegisterByGame;
import top.toobee.spring.controller.dto.RegisterRequest;
import top.toobee.spring.result.ChangePasswordResult;
import top.toobee.spring.result.LoginResult;
import top.toobee.spring.result.RegisterResult;

public interface IUserService extends UserDetailsService {
    LoginResult loginAndGetToken(InetAddress ip, String username, String password);

    RegisterResult registerFromRequest(InetAddress ip, RegisterRequest request);

    RegisterResult registerFromGame(InetAddress ip, RegisterByGame request);

    ChangePasswordResult changePassword(String username, String oldPassword, String newPassword);

    JsonNode getUserProfile(String username);
}
