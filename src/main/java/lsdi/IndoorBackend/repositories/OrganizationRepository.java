package lsdi.IndoorBackend.repositories;

import lsdi.IndoorBackend.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

    @Query("""
        select o
        from OrganizationEntity o
        join o.indoorEnvironments ie
        where ie.id = :indoorEnvironmentId
    """)
    Optional<OrganizationEntity> findByIndoorEnvironmentId(@Param("indoorEnvironmentId") Long indoorEnvironmentId);
}
