package top.toobee.spring.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.toobee.spring.domain.response.ChangePasswordResult;
import top.toobee.spring.domain.response.LoginResult;
import top.toobee.spring.entity.ProfileEntity;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.entity.UserLoginLogEntity;
import top.toobee.spring.repository.ProfileRepository;
import top.toobee.spring.repository.UserLoginLogRepository;
import top.toobee.spring.repository.UserRepository;
import top.toobee.spring.service.AmqpService;
import top.toobee.spring.service.IUserService;
import top.toobee.spring.service.PlayerService;
import top.toobee.spring.utils.DynamicTtlCache;
import top.toobee.spring.utils.JwtUtil;

import java.net.InetAddress;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements IUserService {
    public static final Pattern NAME_MATCHER = Pattern.compile("^[a-zA-Z0-9_.-]{2,20}$");

    private final Random random = new Random();
    private final ProfileRepository profileRepository;
    private final UserLoginLogRepository userLoginLogRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final PlayerService playerService;
    private final AmqpService amqpService;
    private final DynamicTtlCache dynamicTtlCache;

    @Autowired
    public UserServiceImpl(ProfileRepository profileRepository, UserRepository userRepository, UserLoginLogRepository userLoginLogRepository,
                           JwtUtil jwtUtil, PasswordEncoder passwordEncoder, PlayerService playerService, AmqpService amqpService, DynamicTtlCache dynamicTtlCache) {
        this.profileRepository = profileRepository;
        this.userLoginLogRepository = userLoginLogRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.playerService = playerService;
        this.amqpService = amqpService;
        this.dynamicTtlCache = dynamicTtlCache;
    }

    @Override
    public UserEntity findUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public UserEntity findUserByName(String name) {
        return userRepository.findByName(name).orElse(null);
    }

    @Override
    public LoginResult getLoginInfo(InetAddress ip, String name, String password) {
        return NAME_MATCHER.matcher(name).matches() ? userRepository
                .findPasswordByName(name)
                .map(s -> passwordEncoder.matches(s, password) ? LoginResult.OK : LoginResult.WRONG_PASSWORD)
                .orElseGet(() -> {
                    var opt = playerService.isFakeOf(name);
                    if (opt.isEmpty())
                        return LoginResult.UNKNOWN_PLAYER;
                    if (opt.get())
                        return LoginResult.FAKE_PLAYER;
                    return amqpService
                            .verifyPasswordFromGame(name, password)
                            .map(b -> b ? LoginResult.CREATED : LoginResult.WRONG_PASSWORD)
                            .orElse(LoginResult.UNREGISTERED);
                })
        : LoginResult.ILLEGAL_USERNAME;
        /* 换种写法
        if (!NAME_MATCHER.matcher(name).matches())
            return LoginResult.ILLEGAL_USERNAME;
        final var opt1 = userRepository.findPasswordByName(name);
        if (opt1.isPresent())
            return passwordEncoder.matches(password, opt1.get()) ? LoginResult.OK : LoginResult.WRONG_PASSWORD;
        final var opt2 = playerService.isFakeOf(name);
        if (opt2.isPresent())
            return opt2.get() ? LoginResult.FAKE_PLAYER : LoginResult.UNREGISTERED;
        final var opt3 = amqpService.verifyPasswordFromGame(name, password);
        if (opt3.isPresent())
            return opt3.get() ? LoginResult.CREATED : LoginResult.WRONG_PASSWORD;
        return LoginResult.UNKNOWN_PLAYER;
         */
    }

    @Override
    public ChangePasswordResult changePassword(String token, String oldOriginalPassword, String newOriginalPassword) {
        final int len = newOriginalPassword.length();
        if (len < 4 || len > 30)
            return ChangePasswordResult.ILLEGAL_NEW_PASSWORD;
        if (oldOriginalPassword.equals(newOriginalPassword))
            return ChangePasswordResult.SAME_PASSWORD;


        final var isValid = dynamicTtlCache.get(token);
        if (isValid == null)
            return ChangePasswordResult.UNKNOWN_USER;

        final var user = userRepository.findByName(jwtUtil.extractSubject(token)).orElse(null);

        if (user == null)
            return ChangePasswordResult.UNKNOWN_USER;
        if (!passwordEncoder.matches(oldOriginalPassword, user.password))
            return ChangePasswordResult.WRONG_OLD_PASSWORD;

        user.password = passwordEncoder.encode(newOriginalPassword);
        userRepository.save(user);
        return ChangePasswordResult.SUCCESS;
    }

    @Override
    @Transactional
    public UserEntity register(String name, String originalPassword) throws IllegalArgumentException, DuplicateKeyException {
        if (!NAME_MATCHER.matcher(name).matches())
            throw new IllegalArgumentException("Illegal username");
        final var user = new UserEntity(name, passwordEncoder.encode(originalPassword));
        final var player = Objects.requireNonNull(playerService.getPlayerByName(name));
        player.user = user;
        userRepository.saveAndFlush(user);
        playerService.save(player);
        profileRepository.save(new ProfileEntity(user));
        return user;
    }

    @Override
    public int afterLogin(InetAddress ip, UserEntity userEntity) {
        final var log = new UserLoginLogEntity(ip, userEntity);
        userLoginLogRepository.save(log);
        return random.nextInt();
    }

    @Override
    public String signAndIssueToken(String name, int mark) {
        return jwtUtil.generateToken(name, mark);
    }

    @Override
    public UserEntity getUserFromToken(String token) {
        final var isValid = dynamicTtlCache.get(token);
        if (isValid == null)
            return null;

        return userRepository.findByName(jwtUtil.extractSubject(token)).orElse(null);
    }

    @Override
    public ResponseEntity<?> tryLoginAndGetToken(InetAddress ip, String username, String password) {
        final var result = getLoginInfo(ip, username, password);
        if (result == LoginResult.OK || result == LoginResult.CREATED) {
            final var user = result == LoginResult.OK ? findUserByName(username) : register(username, password);
            final var mark = afterLogin(ip, user);
            final var token = signAndIssueToken(username, mark);
            dynamicTtlCache.put(username, token, jwtUtil.getExpirationDate(token));
            return ResponseEntity.ok(token);
        }
        return result.wrap(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"))
                .toUserDetails();
    }
}
