package lsdi.IndoorBackend.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lsdi.IndoorBackend.domain.model.IndoorEnvironment;
import lsdi.IndoorBackend.domain.model.IndoorInference;
import lsdi.IndoorBackend.dtos.IndoorInferenceDTO;
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
    public void addInferences(IndoorInferenceDTO regionFingerprints) {
        IndoorInference fingerprints = IndoorInference.Mapper.fromDTO(regionFingerprints);

        OrganizationEntity organization = organizationRepository
                .findById(fingerprints.getOrganizationId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Organization not found")
                );

        for (IndoorEnvironment r: fingerprints.getIndoorEnvironments()) {
            if (!indoorEnvironmentRepository.existsByEnvironmentName(r.getName())) {
                //TODO: verify if exist environment related with org with same name
            }

            IndoorEnvironmentEntity indoorEnvironment = new IndoorEnvironmentEntity(
                r.getName(),
                r.getBeaconFingerprints(),
                organization
            );

            organization.addIndoorEnvironment(indoorEnvironment);
            indoorEnvironmentRepository.save(indoorEnvironment);
        }

        organizationRepository.save(organization);
    }

    public IndoorInferenceDTO getIndoorInferenceById(Long organizationId) {
        OrganizationEntity organization = organizationRepository
                .findById(organizationId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Organization not found")
                );

        return IndoorInferenceDTO.fromDomain(organization);
    }
}
