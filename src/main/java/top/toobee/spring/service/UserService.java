package top.toobee.spring.service;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.toobee.spring.entity.ProfileEntity;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.repository.UserRepository;
import top.toobee.spring.utils.JwtUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    public static final Pattern NAME_MATCHER = Pattern.compile("^[a-zA-Z0-9_.-]{2,20}$");

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RabbitTemplate rabbitTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository, JwtUtil jwtUtil, RabbitTemplate rabbitTemplate) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.rabbitTemplate = rabbitTemplate;
    }

    public @Nullable UserEntity findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public @NonNull UserEntity register(@NonNull String name, @NonNull String password) {
        if (userRepository.findByName(name).isPresent()) {
            throw new DuplicateKeyException("Username already exists");
        }
        if (!NAME_MATCHER.matcher(name).matches()) {
            throw new IllegalArgumentException("Username is invalid");
        }
        final var userEntity = new UserEntity(name, password);
        ProfileEntity profile = new ProfileEntity();
        profile.user = userEntity;
        entityManager.persist(profile);
        entityManager.persist(userEntity);
        return userEntity;
    }

    public @NonNull UserEntity createRandom() {
        return this.register("random" + (int) System.currentTimeMillis(), "123456");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        return new User(user.name,user.password,new ArrayList<>());
    }

    @Transactional
    public @NonNull Map<String,String> login(@NonNull String name, @NonNull String password) {
        Optional<UserEntity> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            String queueName = "toobee";
            String userInfo = name + ";" + password;
            String str = "";
            Object o = rabbitTemplate.convertSendAndReceive(queueName, userInfo);
            if (o instanceof byte[]) {
                 str = new String((byte[]) o);
            }else if (o!=null){
                str = o.toString();
            }
            if (o == null || str.equals("NOT_REGISTERED")) {
                return Map.of("error", "用户不存在");
            }else if (str.equals("WRONG")) {
                return Map.of("error", "密码错误");
            }
            //把用户信息保存到数据库
            register(name,password);
            //重新查询获取用户信息
            user = userRepository.findByName(name);
        }
        else {
            if (!user.get().password.equals(password)) {
                return Map.of("error", "密码错误");
            }
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(name, password);
        String token = jwtUtil.generateToken((String) auth.getPrincipal(), auth.getAuthorities());
        //更新登录时间
        try {
            userRepository.updateLoginTime(user.get().id);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return Map.of("error", "更新登录时间失败,用户不存在!");
        }
        return Map.of("token", token);
    }

}
