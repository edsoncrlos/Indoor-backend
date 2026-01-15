package lsdi.IndoorBackend.dtos;

import java.time.Instant;

public record OrganizationHierarchyLocationDTO(
        Long organizationId,
        Instant timestamp,
        String organizationName,
        IndoorEnvironmentHierarchyLocationDTO environment
) {
}
