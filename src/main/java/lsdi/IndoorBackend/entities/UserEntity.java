package lsdi.IndoorBackend.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "TB_USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    @Column(nullable = false, unique = true)
    private UUID mobileIdentifier;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    public UserEntity(String name, String email, UUID mobileIdentifier) {
        this.name = name;
        this.email = email;
        this.mobileIdentifier = mobileIdentifier;
    }
}
