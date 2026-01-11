package lsdi.IndoorBackend.dtos;

import java.util.UUID;

public record UserDTO(
        Long id,
        String name,
        String email,
        UUID mobileIdentifier
) {
}
