package lsdi.IndoorBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.OrganizationDTO;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Organization {
    private Long id;
    private String name;
    private List<IndoorEnvironment> indoorEnvironments;
    private CEP cep;
    private Long parentId;

    public Organization(String name, String cep, Long parentId) {
        this.name = name;
        this.parentId = parentId;

        if (cep != null) {
            this.cep = new CEP(cep);
        }
    }

    public Organization(Long id, String name, IndoorEnvironment indoorEnvironments) {
        this.id = id;
        this.name = name;
        this.indoorEnvironments = new ArrayList<>(1);
        this.indoorEnvironments.add(indoorEnvironments);
    }

    public Organization(Long id, List<IndoorEnvironment> indoorEnvironments) {
        this.id = id;
        this.indoorEnvironments = indoorEnvironments;
    }

    public boolean isRoot() {
        if (parentId == null) {
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
