package top.toobee.spring.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import top.toobee.spring.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends JpaRepository<@NonNull ProfileEntity, @NonNull Integer> {}
