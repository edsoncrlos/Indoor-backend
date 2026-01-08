package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lsdi.IndoorBackend.entities.OrganizationEntity;

import java.util.List;

public record OrganizationDTO(
        @NotNull
        @Positive
        Long organizationId,
        @NotNull
        List<IndoorEnvironmentDTO> indoorEnvironments
) {
    public static OrganizationDTO fromDomain(OrganizationEntity domain) {

        List<IndoorEnvironmentDTO> environments =
                domain.getIndoorEnvironments()
                        .stream()
                        .map(IndoorEnvironmentDTO::fromDomain)
                        .toList();

        return new OrganizationDTO(
                domain.getId(),
                environments
        );
    }
}
