package top.toobee.spring.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.toobee.spring.entity.PlayerEntity;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Integer> {
    Optional<Boolean> findFakeByName(String name);

    Optional<PlayerEntity> findByName(String name);
}
