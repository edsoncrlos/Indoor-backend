package lsdi.IndoorBackend.dtos;

import lsdi.IndoorBackend.domain.model.IndoorEnvironment;

public record IndoorEnvironmentHierarchyLocationDTO(
        Long id,
        String name,
        IndoorEnvironmentHierarchyLocationDTO child
) {
    public static IndoorEnvironmentHierarchyLocationDTO fromDomain(IndoorEnvironment domain) {
        IndoorEnvironmentHierarchyLocationDTO child =
                domain.getChildren().stream()
                        .findFirst()
                        .map(IndoorEnvironmentHierarchyLocationDTO::fromDomain)
                        .orElse(null);


        return new IndoorEnvironmentHierarchyLocationDTO(
                domain.getId(),
                domain.getName(),
                child
        );
    }
}
