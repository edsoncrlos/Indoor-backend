package lsdi.IndoorBackend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "TB_USER_INDOOR_ENVIRONMENT")
@Getter
public class UserIndoorEnvironmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "indoor_environment_id", nullable = false)
    private IndoorEnvironmentEntity indoorEnvironment;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public UserIndoorEnvironmentEntity(
            UserEntity user,
            IndoorEnvironmentEntity indoorEnvironment
    ) {
        this.user = user;
        this.indoorEnvironment = indoorEnvironment;
    }

}
