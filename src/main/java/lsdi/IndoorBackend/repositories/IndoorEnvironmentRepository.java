package lsdi.IndoorBackend.repositories;

import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndoorEnvironmentRepository extends JpaRepository<IndoorEnvironmentEntity, Long> {
    public boolean existsByEnvironmentName(String environmentName);
}
