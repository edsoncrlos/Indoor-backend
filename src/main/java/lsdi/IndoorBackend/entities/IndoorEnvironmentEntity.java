package lsdi.IndoorBackend.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lsdi.IndoorBackend.domain.model.BeaconSignalStatistics;
import lsdi.IndoorBackend.entities.converter.BeaconsSignalStatisticsJsonConverter;
import org.hibernate.annotations.ColumnTransformer;

import java.util.*;

@Entity
@Table(name = "TB_INDOOR_ENVIRONMENT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class IndoorEnvironmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String environmentName;

    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = BeaconsSignalStatisticsJsonConverter.class)
    private List<BeaconSignalStatistics> beaconsSignalStatistics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_indoor_environment_id")
    private IndoorEnvironmentEntity parentIndoorEnvironment;

    @OneToMany(
            mappedBy = "parentIndoorEnvironment",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<IndoorEnvironmentEntity> childIndoorEnvironments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_organization_id")
    @Setter
    private OrganizationEntity organization;

    @OneToMany(mappedBy = "indoorEnvironment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserIndoorEnvironmentEntity> users = new HashSet<>();

    // root constructor
    public IndoorEnvironmentEntity(
            String environmentName,
            List<BeaconSignalStatistics> beaconsSignalStatistics,
            OrganizationEntity organization
    ) {
        this.environmentName = environmentName;
        this.beaconsSignalStatistics = beaconsSignalStatistics;
        this.organization = organization;
        this.parentIndoorEnvironment = null;
    }

    // child constructor
    public IndoorEnvironmentEntity(
            String environmentName,
            List<BeaconSignalStatistics> beaconsSignalStatistics,
            IndoorEnvironmentEntity parentIndoorEnvironment
    ) {
        this.environmentName = environmentName;
        this.beaconsSignalStatistics = beaconsSignalStatistics;
        this.parentIndoorEnvironment = Objects.requireNonNull(parentIndoorEnvironment);
        this.organization = parentIndoorEnvironment.getOrganization();

        parentIndoorEnvironment.addChildIndoorEnvironment(this);
    }

    public void addChildIndoorEnvironment(IndoorEnvironmentEntity child) {
        Objects.requireNonNull(child);
        child.parentIndoorEnvironment = this;
        child.organization = this.organization;
        this.childIndoorEnvironments.add(child);
    }

}
