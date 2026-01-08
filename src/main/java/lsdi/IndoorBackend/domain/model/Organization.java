package lsdi.IndoorBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.OrganizationDTO;

import java.util.List;

@AllArgsConstructor
@Getter
public class Organization {
    private Long organizationId;
    private List<IndoorEnvironment> indoorEnvironments;

    public static class Mapper {
        public static Organization fromDTO(OrganizationDTO dto) {
            List<IndoorEnvironment> indoorEnvironments =
                    dto.indoorEnvironments()
                            .stream()
                            .map(IndoorEnvironment.Mapper::fromDTO)
                            .toList();

            return new Organization(
                    dto.organizationId(),
                    indoorEnvironments
            );
        }
    }
}
