package lsdi.IndoorBackend.repositories;

import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;
import lsdi.IndoorBackend.repositories.projections.IndoorEnvironmentProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndoorEnvironmentRepository extends JpaRepository<IndoorEnvironmentEntity, Long> {
    boolean existsByName(String environmentName);

    @Query(value = """
    WITH RECURSIVE parents (id, name, parent_id, parent_organization_id) AS (
        SELECT id, name, parent_id, parent_organization_id
        FROM TB_INDOOR_ENVIRONMENT
        WHERE id = :id

        UNION ALL

        SELECT child.id, child.name, child.parent_id, child.parent_organization_id
        FROM TB_INDOOR_ENVIRONMENT child
        INNER JOIN parents p ON child.id = p.parent_id
    )
    SELECT id, name, parent_id FROM parents
    ORDER BY id ASC
    """, nativeQuery = true)
    List<IndoorEnvironmentProjection> findParentsRecursive(@Param("id") Long id);
}
