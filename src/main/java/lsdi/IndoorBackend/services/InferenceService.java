package lsdi.IndoorBackend.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lsdi.IndoorBackend.domain.model.IndoorEnvironment;
import lsdi.IndoorBackend.domain.model.Organization;
import lsdi.IndoorBackend.dtos.InferenceIndoorEnvironmentDTO;
import lsdi.IndoorBackend.dtos.OrganizationDTO;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;
import lsdi.IndoorBackend.entities.OrganizationEntity;
import lsdi.IndoorBackend.entities.UserEntity;
import lsdi.IndoorBackend.entities.UserIndoorEnvironmentEntity;
import lsdi.IndoorBackend.entities.converter.IndoorEnvironmentEntityMapper;
import lsdi.IndoorBackend.repositories.IndoorEnvironmentRepository;
import lsdi.IndoorBackend.repositories.OrganizationRepository;
import lsdi.IndoorBackend.repositories.UserIndoorEnvironmentRepository;
import lsdi.IndoorBackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class InferenceService {

    private final OrganizationRepository organizationRepository;
    private final IndoorEnvironmentRepository indoorEnvironmentRepository;
    private final UserRepository userRepository;
    private final UserIndoorEnvironmentRepository userIndoorEnvironmentRepository;

    public InferenceService(
            OrganizationRepository organizationRepository,
            IndoorEnvironmentRepository indoorEnvironmentRepository,
            UserRepository userRepository,
            UserIndoorEnvironmentRepository userIndoorEnvironmentRepository
    ) {
        this.organizationRepository = organizationRepository;
        this.indoorEnvironmentRepository = indoorEnvironmentRepository;
        this.userRepository = userRepository;
        this.userIndoorEnvironmentRepository = userIndoorEnvironmentRepository;
    }

    @Transactional
    public void saveOrganizationIndoorTraining(OrganizationDTO organizationDTO) {
        Organization organization = Organization.Mapper.fromDTO(organizationDTO);

        OrganizationEntity organizationEntity = organizationRepository
                .findById(organization.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Organization not found")
                );

        for (IndoorEnvironment indoorEnvironment: organization.getIndoorEnvironments()) {
            if (!indoorEnvironmentRepository.existsByName(indoorEnvironment.getName())) {
                //TODO: verify if exist environment related with org with same name
            }

            IndoorEnvironmentEntity indoorEnvironmentEntity = IndoorEnvironmentEntityMapper
                    .toEntity(
                            indoorEnvironment,
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

    public void addInference(InferenceIndoorEnvironmentDTO inferenceIndoorEnvironmentDTO) {
        UUID mobileId = inferenceIndoorEnvironmentDTO.mobileId();
        Long environmentId = inferenceIndoorEnvironmentDTO.environmentId();

        UserEntity user = userRepository
                .findByMobileIdentifier(mobileId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found")
                );

        IndoorEnvironmentEntity environmentEntity = indoorEnvironmentRepository
                .findById(environmentId)
                .orElseThrow(() ->
                        new EntityNotFoundException("IndoorEnvironment not found")
                );

        UserIndoorEnvironmentEntity userIndoorEnvironment = new UserIndoorEnvironmentEntity(
                user,
                environmentEntity
        );

        userIndoorEnvironmentRepository.save(userIndoorEnvironment);
    }

    @Transactional
    public Organization getUserLastLocation(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        UserIndoorEnvironmentEntity userIndoorEnvironment = userIndoorEnvironmentRepository
                .findFirstByUser_IdOrderByCreatedAtDesc(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User never send predict")
                );

        return getOrganizationWithParentIndoorEnvironments(
                userIndoorEnvironment.getIndoorEnvironment()
        );
    }

    @Transactional
    private Organization getOrganizationWithParentIndoorEnvironments(IndoorEnvironmentEntity indoorEnvironmentEntity) {
        Set<Long> visited = new HashSet<>();
        IndoorEnvironmentEntity current = indoorEnvironmentEntity;

        IndoorEnvironment childIndoorEnvironment = new IndoorEnvironment(
            current.getId(),
            current.getName()
        );
        IndoorEnvironment parentIndoorEnvironment;

        while (current.getParent() != null) {
            if (!visited.add(current.getId())) {
                throw new IllegalStateException("Cycle detected in the hierarchy");
            }
            current = current.getParent();

            parentIndoorEnvironment = new IndoorEnvironment(
                    current.getId(),
                    current.getName()
            );

            parentIndoorEnvironment.addChildIndoorEnvironment(childIndoorEnvironment);
            childIndoorEnvironment = parentIndoorEnvironment;
        }
        OrganizationEntity organizationEntity = current.getOrganization();

        return new Organization(
                organizationEntity.getId(),
                organizationEntity.getName(),
                childIndoorEnvironment
        );
    }
}
