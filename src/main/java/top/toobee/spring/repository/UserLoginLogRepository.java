package top.toobee.spring.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.toobee.spring.entity.UserLoginLogEntity;

@Repository
public interface UserLoginLogRepository
        extends JpaRepository<@NonNull UserLoginLogEntity, @NonNull Long> {}
