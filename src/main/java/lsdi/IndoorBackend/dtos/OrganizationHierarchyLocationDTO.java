package lsdi.IndoorBackend.dtos;

import lsdi.IndoorBackend.domain.model.Organization;

public record OrganizationHierarchyLocationDTO(
        Long organizationId,
        String organizationName,
        IndoorEnvironmentHierarchyLocationDTO environment
) {
    public static OrganizationHierarchyLocationDTO fromDomain(Organization domain) {

        IndoorEnvironmentHierarchyLocationDTO child =
                domain.getIndoorEnvironments().stream()
                        .findFirst()
                        .map(IndoorEnvironmentHierarchyLocationDTO::fromDomain)
                        .orElse(null);

        return new OrganizationHierarchyLocationDTO(
                domain.getId(),
                domain.getName(),
                child
        );
    }
}
