package top.toobee.spring.service;

import jakarta.annotation.Nullable;
import org.apache.catalina.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.repository.UserRepository;
import top.toobee.spring.utils.JwtUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public @Nullable UserEntity findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public @NonNull UserEntity register(@NonNull String name, @NonNull String password) {
        String str = "^[a-zA-Z0-9_.-]{1,20}";
        if (userRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("用户名已存在");
        }
        if (!name.matches(str)) {
            throw new IllegalArgumentException("用户名格式错误");
        }
        final var userEntity = new UserEntity(name, password);
        userRepository.saveAndFlush(userEntity);
        return userEntity;
    }

    public @NonNull UserEntity createRandom() {
        return this.register("random" + (int) System.currentTimeMillis(), "123456");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return new org.springframework.security.core.userdetails.User(
                user.name,user.password,new ArrayList<>()
        );
    }

    public @NonNull Map<String,String> login(@NonNull String name, @NonNull String password) {
        Optional<UserEntity> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            String queuqeName = "toobee";
            String userInfo = name+";"+password;
            rabbitTemplate.convertAndSend(queuqeName, userInfo);
            return  Map.of("error","用户不存在");
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(name,password);
        String token = jwtUtil.generateToken((String) auth.getPrincipal(),auth.getAuthorities());
            return Map.of("token",token);
    }
}
