package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;

public record OrganizationDTO(
        @NotBlank
        String name,
        String cep,
        Long parentOrganizationId
) {
}
