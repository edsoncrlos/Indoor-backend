package lsdi.IndoorBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.IndoorInferenceDTO;

import java.util.List;

@AllArgsConstructor
@Getter
public class IndoorInference {
    private Long organizationId;
    private List<IndoorEnvironment> indoorEnvironments;

    public static class Mapper {
        public static IndoorInference fromDTO(IndoorInferenceDTO dto) {
            List<IndoorEnvironment> indoorEnvironments =
                    dto.indoorEnvironments()
                            .stream()
                            .map(IndoorEnvironment.Mapper::fromDTO)
                            .toList();

            return new IndoorInference(
                    dto.organizationId(),
                    indoorEnvironments
            );
        }
    }
}
