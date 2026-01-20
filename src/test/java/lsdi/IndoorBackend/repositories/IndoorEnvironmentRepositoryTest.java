package lsdi.IndoorBackend.repositories;

import lsdi.IndoorBackend.domain.model.IndoorEnvironment;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;
import lsdi.IndoorBackend.entities.OrganizationEntity;
import lsdi.IndoorBackend.repositories.projections.IndoorEnvironmentProjection;
import lsdi.IndoorBackend.services.InferenceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Sql(
        scripts = {
                "/import_organizations.sql",
                "/import_recursivelyIndoorEnvironments.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class IndoorEnvironmentRepositoryTest {
    @Autowired
    private IndoorEnvironmentRepository indoorEnvironmentRepository;

    @Test
    public void findParentsRecursive_ReturnsListParents() {
        List<IndoorEnvironmentProjection> resultsProjection = indoorEnvironmentRepository.findParentsRecursive(204L);

        List<Long> expectedParentIds =
                Arrays.asList(null, 200L, 201L, 202L, 203L);

        List<Long> actualParentIds = resultsProjection.stream()
                .map(IndoorEnvironmentProjection::getParentId)
                .toList();


        assertEquals(expectedParentIds, actualParentIds);
    }
}
