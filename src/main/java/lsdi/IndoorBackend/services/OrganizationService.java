package lsdi.IndoorBackend.services;

import jakarta.persistence.EntityNotFoundException;
import lsdi.IndoorBackend.domain.model.CEP;
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

    public Long save(CreateOrganizationDTO organization) {
        OrganizationEntity organizationEntity = createOrganization(organization);

        OrganizationEntity organizationSaved = organizationRepository.save(organizationEntity);
        return organizationSaved.getId();
    }

    private OrganizationEntity createOrganization(CreateOrganizationDTO organization) {
        CEP cep = null;
        if (organization.cep() != null) {
            cep = new CEP(organization.cep());
        }

        if (organization.parentOrganizationId() == null) {
            return new OrganizationEntity(organization.name(), cep);
        }

        OrganizationEntity parent = organizationRepository
                .findById(organization.parentOrganizationId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Organization not found")
                );

        return new OrganizationEntity(organization.name(), cep, parent);
    }
}
