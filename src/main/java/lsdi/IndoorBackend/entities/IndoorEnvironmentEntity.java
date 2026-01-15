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
    private String name;

    @Column(columnDefinition = "jsonb", nullable = false)
    @ColumnTransformer(write = "?::jsonb")
    @Convert(converter = BeaconsSignalStatisticsJsonConverter.class)
    private List<BeaconSignalStatistics> beaconsSignalStatistics;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private IndoorEnvironmentEntity parent;

    @OneToMany(
            mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<IndoorEnvironmentEntity> children = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_organization_id")
    @Setter
    private OrganizationEntity organization;

    @OneToMany(mappedBy = "indoorEnvironment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserIndoorEnvironmentEntity> users = new HashSet<>();

    // root constructor
    public IndoorEnvironmentEntity(
            String name,
            List<BeaconSignalStatistics> beaconsSignalStatistics,
            OrganizationEntity organization
    ) {
        this.name = Objects.requireNonNull(name);
        this.beaconsSignalStatistics = Objects.requireNonNull(beaconsSignalStatistics);
        this.organization = Objects.requireNonNull(organization);
        this.parent = null;
    }

    // child constructor
    public IndoorEnvironmentEntity(
            String name,
            List<BeaconSignalStatistics> beaconsSignalStatistics,
            IndoorEnvironmentEntity parent
    ) {
        this.name = Objects.requireNonNull(name);
        this.beaconsSignalStatistics = Objects.requireNonNull(beaconsSignalStatistics);
        this.parent = Objects.requireNonNull(parent);
        this.organization = parent.getOrganization();

        parent.addChildIndoorEnvironment(this);
    }

    public void addChildIndoorEnvironment(IndoorEnvironmentEntity child) {
        Objects.requireNonNull(child);
        child.parent = this;
        child.organization = this.organization;
        this.children.add(child);
    }

}
