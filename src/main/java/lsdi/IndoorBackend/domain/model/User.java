package lsdi.IndoorBackend.domain.model;

import lombok.Getter;
import lsdi.IndoorBackend.dtos.UserDTO;

import java.util.Objects;
import java.util.UUID;

@Getter
public class User {
    private Long id;

    private String name;

    private String email;

    private UUID mobileIdentifier;

    public User(Long id, String name, String email, UUID mobileIdentifier) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileIdentifier = mobileIdentifier;
    }

    public static class Mapper {

        public static User fromDTO(UserDTO dto) {
            Objects.requireNonNull(dto);

            return new User(
                    dto.id(),
                    dto.name(),
                    dto.email(),
                    dto.mobileIdentifier()
            );
        }
    }
}
