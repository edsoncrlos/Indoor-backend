package lsdi.IndoorBackend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lsdi.IndoorBackend.entities.OrganizationEntity;

import java.util.List;

public record OrganizationDTO(
        @NotNull(message = "The organization ID must be provided")
        @Positive(message = "The organization ID must be a positive number")
        Long organizationId,

        @NotNull(message = "At least one indoor environment must be provided")
        @Valid
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
