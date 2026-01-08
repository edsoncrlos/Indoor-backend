package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateOrganizationDTO(
        @NotBlank
        String name,
        String cep,
        Long parentOrganizationId
) {
}
