package lsdi.IndoorBackend.repositories;

import lsdi.IndoorBackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByMobileIdentifier(UUID mobileIdentifier);
}
