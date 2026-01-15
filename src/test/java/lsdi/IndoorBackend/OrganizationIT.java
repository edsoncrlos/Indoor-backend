package lsdi.IndoorBackend;

import lsdi.IndoorBackend.common.ApiPaths;
import lsdi.IndoorBackend.dtos.CreateOrganizationDTO;
import lsdi.IndoorBackend.entities.OrganizationEntity;
import lsdi.IndoorBackend.repositories.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static lsdi.IndoorBackend.common.OrganizationConstants.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class OrganizationIT {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void addRootOrganization() {
        ResponseEntity<Long> responseRootOrg = restTemplate.postForEntity(ApiPaths.ORGANIZATION, ROOT_ORG, Long.class);

        assertThat(responseRootOrg.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long idRoot = responseRootOrg.getBody();
        assertThat(idRoot).isNotNull();

        OrganizationEntity savedRoot = organizationRepository.findById(idRoot).orElseThrow();

        assertThat(savedRoot.getName()).isEqualTo(ROOT_ORG_NAME);
        assertThat(savedRoot.getCep()).isNull();
    }

    @Test
    public void addChildOrganization() {
        ResponseEntity<Long> responseRootOrg = restTemplate.postForEntity(ApiPaths.ORGANIZATION, ROOT_ORG, Long.class);
        Long idRootOrganization = responseRootOrg.getBody();

        CreateOrganizationDTO CHILD_ORGANIZATION = new CreateOrganizationDTO(
                CHILD_ORG_NAME,
                CEP,
                idRootOrganization
        );

        ResponseEntity<Long> responseChildOrg = restTemplate.postForEntity(ApiPaths.ORGANIZATION, CHILD_ORGANIZATION, Long.class);

        assertThat(responseChildOrg.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long idChildOrg = responseChildOrg.getBody();
        assertThat(idChildOrg).isNotNull();

        OrganizationEntity saved = organizationRepository.findById(idChildOrg).orElseThrow();

        assertThat(saved.getName()).isEqualTo(CHILD_ORG_NAME);
        assertThat(saved.getCep().getFormatted()).isEqualTo(CEP);
        assertThat(saved.getParent().getId()).isEqualTo(idRootOrganization);
    }
}
