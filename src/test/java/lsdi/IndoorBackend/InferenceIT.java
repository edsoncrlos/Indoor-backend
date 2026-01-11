package lsdi.IndoorBackend;

import lsdi.IndoorBackend.common.ApiPaths;
import lsdi.IndoorBackend.dtos.OrganizationDTO;
import lsdi.IndoorBackend.repositories.IndoorEnvironmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static lsdi.IndoorBackend.common.InferenceConstants.indoorEnvironmentsDTO;
import static lsdi.IndoorBackend.common.OrganizationConstants.ROOT_ORG;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InferenceIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IndoorEnvironmentRepository indoorEnvironmentRepository;

    @Test
    public void addInference() {
        Long idOrg = (restTemplate.postForEntity(ApiPaths.ORGANIZATION, ROOT_ORG, Long.class)).getBody();

        OrganizationDTO inferenceDTO = new OrganizationDTO(
                idOrg,
                indoorEnvironmentsDTO
        );

        ResponseEntity<String> response = restTemplate.postForEntity(ApiPaths.INFERENCE+ApiPaths.OFFLINE, inferenceDTO, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
    }
}
