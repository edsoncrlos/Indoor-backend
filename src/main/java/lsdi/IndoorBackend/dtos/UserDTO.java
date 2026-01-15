package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserDTO(
        Long id,

        @NotBlank(message = "The user name must not be blank")
        String name,

        @Email(message = "The email address must be valid")
        String email,

        @NotNull(message = "The mobile identifier must be provided")
        UUID mobileIdentifier
) {
}
