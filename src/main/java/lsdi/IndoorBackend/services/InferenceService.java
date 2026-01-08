package lsdi.IndoorBackend.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lsdi.IndoorBackend.domain.model.IndoorEnvironment;
import lsdi.IndoorBackend.domain.model.Organization;
import lsdi.IndoorBackend.dtos.OrganizationDTO;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;
import lsdi.IndoorBackend.entities.OrganizationEntity;
import lsdi.IndoorBackend.repositories.IndoorEnvironmentRepository;
import lsdi.IndoorBackend.repositories.OrganizationRepository;
import org.springframework.stereotype.Service;

@Service
public class InferenceService {

    private final OrganizationRepository organizationRepository;
    private final IndoorEnvironmentRepository indoorEnvironmentRepository;

    public InferenceService(
            OrganizationRepository organizationRepository,
            IndoorEnvironmentRepository indoorEnvironmentRepository
    ) {
        this.organizationRepository = organizationRepository;
        this.indoorEnvironmentRepository = indoorEnvironmentRepository;
    }

    @Transactional
    public void addInferences(OrganizationDTO organizationDTO) {
        Organization organization = Organization.Mapper.fromDTO(organizationDTO);

        OrganizationEntity organizationEntity = organizationRepository
                .findById(organization.getOrganizationId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Organization not found")
                );

        for (IndoorEnvironment indoorEnvironment: organization.getIndoorEnvironments()) {
            if (!indoorEnvironmentRepository.existsByEnvironmentName(indoorEnvironment.getName())) {
                //TODO: verify if exist environment related with org with same name
            }

            IndoorEnvironmentEntity indoorEnvironmentEntity = new IndoorEnvironmentEntity(
                indoorEnvironment.getName(),
                indoorEnvironment.getBeaconsSignalStatistics(),
                organizationEntity
            );

            organizationEntity.addIndoorEnvironment(indoorEnvironmentEntity);
            indoorEnvironmentRepository.save(indoorEnvironmentEntity);
        }

        organizationRepository.save(organizationEntity);
    }

    public OrganizationDTO getIndoorInferenceById(Long organizationId) {
        OrganizationEntity organization = organizationRepository
                .findById(organizationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Organization not found")
                );

        return OrganizationDTO.fromDomain(organization);
    }
}
