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
    private String name;

    @Column(name = "cep", length = 8)
    @Convert(converter = CEPConverter.class)
    private CEP cep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private OrganizationEntity parent;

    @OneToMany(mappedBy = "parent")
    private final List<OrganizationEntity> children = new ArrayList<>();

    @OneToMany(
            mappedBy = "organization",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<IndoorEnvironmentEntity> indoorEnvironments = new ArrayList<>();

    // rootConstructor
    public OrganizationEntity(
            String name,
            CEP cep
    ) {
        this.name = Objects.requireNonNull(name);
        this.cep = cep;
        this.parent = null;
    }

    // childConstructor
    public OrganizationEntity(
            String name,
            CEP cep,
            OrganizationEntity parent
    ) {
        this.name = Objects.requireNonNull(name);
        this.cep = cep;
        this.parent = Objects.requireNonNull(parent);

        this.parent.addChildOrganization(this);
    }

    public void addChildOrganization(OrganizationEntity child) {
        Objects.requireNonNull(child);
        child.parent = this;
        this.children.add(child);
    }

    public void addIndoorEnvironment(IndoorEnvironmentEntity indoorEnvironment) {
        indoorEnvironment.setOrganization(this);
        this.indoorEnvironments.add(indoorEnvironment);
    }
}
