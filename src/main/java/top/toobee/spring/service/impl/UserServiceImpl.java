package top.toobee.spring.service.impl;

import java.net.InetAddress;
import java.util.Random;
import java.util.regex.Pattern;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.NullNode;
import top.toobee.spring.controller.dto.RegisterByGame;
import top.toobee.spring.controller.dto.RegisterRequest;
import top.toobee.spring.domain.model.UserDetailsImpl;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.repository.UserLoginLogRepository;
import top.toobee.spring.repository.UserRepository;
import top.toobee.spring.result.ChangePasswordResult;
import top.toobee.spring.result.LoginResult;
import top.toobee.spring.result.RegisterResult;
import top.toobee.spring.service.AmqpService;
import top.toobee.spring.service.IUserService;
import top.toobee.spring.utils.DynamicTtlCache;
import top.toobee.spring.utils.JwtUtil;
import top.toobee.spring.utils.RolePermissionData;

@Service
public final class UserServiceImpl implements IUserService {
    public static final Pattern NAME_MATCHER = Pattern.compile("^[a-zA-Z0-9_-]+$");
    private final Random random = new Random();
    private final UserLoginLogRepository userLoginLogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AmqpService amqpService;
    private final DynamicTtlCache dynamicTtlCache;
    private final RolePermissionData rolePermissionData;

    @Autowired
    public UserServiceImpl(
            UserLoginLogRepository userLoginLogRepository,
            UserRepository userRepository,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            AmqpService amqpService,
            DynamicTtlCache dynamicTtlCache,
            RolePermissionData rolePermissionData) {
        this.userLoginLogRepository = userLoginLogRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.amqpService = amqpService;
        this.dynamicTtlCache = dynamicTtlCache;
        this.rolePermissionData = rolePermissionData;
    }

    private String login(InetAddress ip, UserEntity user) {
        // TODO: 开始登录：写入日志、添加JWT缓存。返回JWT Token
        return "";
    }

    @Override
    public LoginResult loginAndGetToken(InetAddress ip, String username, String password) {
        // TODO: 校验用户名密码、账号是否被锁。
        return new LoginResult.Success("");
    }

    @Override
    public RegisterResult registerFromRequest(InetAddress ip, RegisterRequest request) {
        return RegisterResult.RequestSuccess.INSTANCE;
    }

    @Override
    public RegisterResult registerFromGame(InetAddress ip, RegisterByGame request) {
        return new RegisterResult.FromGameSuccess("");
    }

    @Override
    public ChangePasswordResult changePassword(
            String username, String oldPassword, String newPassword) {
        return ChangePasswordResult.SUCCESS;
    }

    @Override
    public JsonNode getUserProfile(String username) {
        return NullNode.instance;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username)
            throws UsernameNotFoundException {
        final var user =
                userRepository
                        .findByName(username)
                        .orElseThrow(() -> UsernameNotFoundException.fromUsername(username));
        return new UserDetailsImpl(
                user.name, user.password, rolePermissionData.getPerms(user.roleId), !user.locked);
    }
}
