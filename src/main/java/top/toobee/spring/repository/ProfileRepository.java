package top.toobee.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import top.toobee.spring.entity.ProfileEntity;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {
}
