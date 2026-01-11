package lsdi.IndoorBackend.repositories;

import lsdi.IndoorBackend.entities.UserIndoorEnvironmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIndoorEnvironmentRepository extends JpaRepository<UserIndoorEnvironmentEntity, Long> {
    Optional<UserIndoorEnvironmentEntity>
    findFirstByUser_IdOrderByCreatedAtDesc(Long userId);
}
