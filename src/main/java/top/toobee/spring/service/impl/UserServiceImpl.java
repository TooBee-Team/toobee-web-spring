package top.toobee.spring.service.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.toobee.spring.domain.response.ChangePasswordResult;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.repository.UserRepository;
import top.toobee.spring.service.IUserService;
import top.toobee.spring.utils.JwtUtil;

import java.net.InetAddress;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserServiceImpl implements IUserService {
    public static final Pattern NAME_MATCHER = Pattern.compile("^[a-zA-Z0-9_.-]{2,20}$");

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public UserEntity findUserById(int id) {
        return null;
    }

    @Override
    public UserEntity findUserByName(String name) {
        return null;
    }

    @Override
    public Boolean verifyPasswordFromGame(String name, String password) {
        return null;
    }

    @Override
    public LoginPair getLoginInfo(InetAddress ip, String name, String password) {
        return null;
    }

    @Override
    public ChangePasswordResult changePassword(String token, String oldOriginalPassword, String newOriginalPassword) {
        String name = jwtUtil.extractUsername(token);

        Optional<UserEntity> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            return ChangePasswordResult.UNKNOWN_USER;
        }
        if (!user.get().password.equals(oldOriginalPassword)) {
            return ChangePasswordResult.WRONG_OLD_PASSWORD;
        }
        if (oldOriginalPassword.equals(newOriginalPassword)) {
            return ChangePasswordResult.SAME_PASSWORD;
        }
        user.get().password=newOriginalPassword;
        userRepository.save(user.get());
        return ChangePasswordResult.SUCCESS;
    }

    @Override
    public UserEntity register(String name, String originalPassword) throws IllegalArgumentException, DuplicateKeyException {
        return null;
    }

    @Override
    public byte afterLogin(InetAddress ip, String name) {
        return 0;
    }

    @Override
    public String signAndIssueToken(String name, InetAddress ip, byte mark) {
        return "";
    }

    @Override
    public UserEntity getUserFromToken(String token) {
        return null;
    }

    @Override
    public ResponseEntity<?> tryLoginAndGetToken(InetAddress ip, String username, String password) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
