package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lsdi.IndoorBackend.entities.OrganizationEntity;

import java.util.List;

public record IndoorInferenceDTO(
        @NotNull
        @Positive
        Long organizationId,
        @NotNull
        List<IndoorEnvironmentDTO> indoorEnvironments
) {
    public static IndoorInferenceDTO fromDomain(OrganizationEntity domain) {

        List<IndoorEnvironmentDTO> environments =
                domain.getIndoorEnvironments()
                        .stream()
                        .map(IndoorEnvironmentDTO::fromDomain)
                        .toList();

        return new IndoorInferenceDTO(
                domain.getId(),
                environments
        );
    }
}
