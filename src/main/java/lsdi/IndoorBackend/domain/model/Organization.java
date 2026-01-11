package lsdi.IndoorBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Organization {
    private Long organizationId;
    private String name;
    private List<IndoorEnvironment> indoorEnvironments;
    private CEP cep;
    private Long parentOrganizationId;

    public Organization(String name, String cep, Long parentOrganizationId) {
        this.name = name;
        this.parentOrganizationId = parentOrganizationId;

        if (cep != null) {
            this.cep = new CEP(cep);
        }
    }

    public Organization(Long organizationId, String name, IndoorEnvironment indoorEnvironments) {
        this.organizationId = organizationId;
        this.name = name;
        this.indoorEnvironments = new ArrayList<>(1);
        this.indoorEnvironments.add(indoorEnvironments);
    }

    public Organization(Long organizationId, List<IndoorEnvironment> indoorEnvironments) {
        this.organizationId = organizationId;
        this.indoorEnvironments = indoorEnvironments;
    }

    public boolean isRoot() {
        if (parentOrganizationId == null) {
            return true;
        }
        return false;
    }

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
