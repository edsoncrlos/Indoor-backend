package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateOrganizationDTO(
        @NotBlank(message = "The organization name must not be blank")
        String name,

        String cep,

        @Positive(message = "The parent organization ID must be a positive number")
        Long parentOrganizationId
) {
}
