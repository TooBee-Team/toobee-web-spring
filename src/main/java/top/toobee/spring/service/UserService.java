package top.toobee.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import top.toobee.spring.entity.UserEntity;
import top.toobee.spring.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public @Nullable UserEntity findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public @NonNull UserEntity register(@NonNull String name, @NonNull String password) {
        final var userEntity = new UserEntity(name, password);
        userRepository.saveAndFlush(userEntity);
        return userEntity;
    }

    public @NonNull UserEntity createRandom() {
        return this.register("random" + (int) System.currentTimeMillis(), "123456");
    }

}
