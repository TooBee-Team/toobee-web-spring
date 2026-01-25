package top.toobee.spring.repository;

import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.toobee.spring.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<@NonNull UserEntity, @NonNull Integer> {
    Optional<UserEntity> findByName(String name);

    Optional<String> findPasswordByName(String name);

    Optional<Integer> findRoleIdByName(String name);
}
