package lsdi.IndoorBackend.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lsdi.IndoorBackend.domain.model.IndoorEnvironment;
import lsdi.IndoorBackend.domain.model.Organization;
import lsdi.IndoorBackend.dtos.IndoorEnvironmentHierarchyLocationDTO;
import lsdi.IndoorBackend.dtos.InferenceIndoorEnvironmentDTO;
import lsdi.IndoorBackend.dtos.OrganizationDTO;
import lsdi.IndoorBackend.dtos.OrganizationHierarchyLocationDTO;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;
import lsdi.IndoorBackend.entities.OrganizationEntity;
import lsdi.IndoorBackend.entities.UserEntity;
import lsdi.IndoorBackend.entities.UserIndoorEnvironmentEntity;
import lsdi.IndoorBackend.entities.converter.IndoorEnvironmentEntityMapper;
import lsdi.IndoorBackend.repositories.IndoorEnvironmentRepository;
import lsdi.IndoorBackend.repositories.OrganizationRepository;
import lsdi.IndoorBackend.repositories.UserIndoorEnvironmentRepository;
import lsdi.IndoorBackend.repositories.UserRepository;
import lsdi.IndoorBackend.repositories.projections.IndoorEnvironmentProjection;
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
    public OrganizationHierarchyLocationDTO getUserLastLocation(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found");
        }

        // get last predict
        UserIndoorEnvironmentEntity userIndoorEnvironment = userIndoorEnvironmentRepository
                .findFirstByUser_IdOrderByCreatedAtDesc(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User never send predict")
                );

        // get parents environments
        Long indoorEnvironmentId = userIndoorEnvironment.getIndoorEnvironment().getId();
        List<IndoorEnvironmentProjection> indoorEnvironmentsProjections = indoorEnvironmentRepository
                .findParentsRecursive(indoorEnvironmentId);
        IndoorEnvironment indoorEnvironment = IndoorEnvironment.Mapper.fromProjection(indoorEnvironmentsProjections, indoorEnvironmentId);

        // get organization
        OrganizationEntity organization = organizationRepository
                .findByIndoorEnvironmentId(indoorEnvironment.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Organization not found")
                );

        OrganizationHierarchyLocationDTO organizationHierarchyLocationDTO = new OrganizationHierarchyLocationDTO(
                organization.getId(),
                userIndoorEnvironment.getCreatedAt(),
                organization.getName(),
                IndoorEnvironmentHierarchyLocationDTO.fromDomain(indoorEnvironment)
        );
        return organizationHierarchyLocationDTO;
    }
}
