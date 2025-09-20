package top.toobee.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.toobee.spring.entity.UserLoginLogEntity;

@Repository
public interface UserLoginLogRepository extends JpaRepository<UserLoginLogEntity, Long> {
}
