package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record IndoorInferenceDTO(
        @NotNull
        @Positive
        Long organizationId,
        @NotNull
        List<IndoorEnvironmentDTO> indoorEnvironments
) {
}
