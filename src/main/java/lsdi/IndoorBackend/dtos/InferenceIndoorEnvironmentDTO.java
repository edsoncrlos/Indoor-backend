package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record InferenceIndoorEnvironmentDTO(
        @NotNull(message = "The mobile identifier must be provided")
        UUID mobileId,

        @NotNull(message = "The environment ID must be provided")
        @Positive(message = "The environment ID must be a positive number")
        Long environmentId
) {
}
