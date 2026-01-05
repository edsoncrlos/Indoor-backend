package lsdi.IndoorBackend.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lsdi.IndoorBackend.domain.model.CEP;
import lsdi.IndoorBackend.entities.converter.CEPConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TB_ORGANIZATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class OrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String organizationName;

    @Column(name = "cep", length = 8)
    @Convert(converter = CEPConverter.class)
    private CEP cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_organization_id")
    private OrganizationEntity parentOrganization;

    @OneToMany(mappedBy = "parentOrganization")
    private final List<OrganizationEntity> childOrganizations = new ArrayList<>();

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<IndoorEnvironmentEntity> indoorEnvironments = new ArrayList<>();

    // rootConstructor
    public OrganizationEntity(
            String organizationName,
            CEP cep
    ) {
        this.organizationName = organizationName;
        this.cep = cep;
        this.parentOrganization = null;
    }

    // childConstructor
    public OrganizationEntity(
            String organizationName,
            CEP cep,
            OrganizationEntity parentOrganization
    ) {
        this.organizationName = Objects.requireNonNull(organizationName);
        this.cep = cep;
        this.parentOrganization = Objects.requireNonNull(parentOrganization);

        this.parentOrganization.addChildOrganization(this);
    }

    public void addChildOrganization(OrganizationEntity child) {
        Objects.requireNonNull(child);
        child.parentOrganization = this;
        this.childOrganizations.add(child);
    }

    public void addIndoorEnvironment(IndoorEnvironmentEntity indoorEnvironment) {
        indoorEnvironment.setOrganization(this);
        this.indoorEnvironments.add(indoorEnvironment);
    }
}
