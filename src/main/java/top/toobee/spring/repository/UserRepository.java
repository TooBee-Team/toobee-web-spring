package top.toobee.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.toobee.spring.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
