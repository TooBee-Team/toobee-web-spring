package top.toobee.spring.service;

import org.springframework.stereotype.Service;
import top.toobee.spring.Repository.UserRepository;
import top.toobee.spring.dto.User;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
