package lsdi.IndoorBackend.services;

import jakarta.persistence.EntityNotFoundException;
import lsdi.IndoorBackend.domain.model.CEP;
import lsdi.IndoorBackend.domain.model.Organization;
import lsdi.IndoorBackend.dtos.CreateOrganizationDTO;
import lsdi.IndoorBackend.entities.OrganizationEntity;
import lsdi.IndoorBackend.repositories.OrganizationRepository;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Long save(CreateOrganizationDTO organizationDTO) {
        Organization organization = new Organization(
                organizationDTO.name(),
                organizationDTO.cep(),
                organizationDTO.parentOrganizationId()
        );

        OrganizationEntity organizationEntity = createOrganizationEntity(organization);

        OrganizationEntity organizationSaved = organizationRepository.save(organizationEntity);
        return organizationSaved.getId();
    }

    private OrganizationEntity createOrganizationEntity(Organization organization) {

        if (organization.isRoot()) {
            return new OrganizationEntity(organization.getName(), organization.getCep());
        }

        OrganizationEntity parent = organizationRepository
                .findById(organization.getParentOrganizationId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Organization not found")
                );

        return new OrganizationEntity(organization.getName(), organization.getCep(), parent);
    }
}
