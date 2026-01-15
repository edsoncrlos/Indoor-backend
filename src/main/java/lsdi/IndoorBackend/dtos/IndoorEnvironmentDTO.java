package lsdi.IndoorBackend.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;

import java.util.List;

public record IndoorEnvironmentDTO(
        @Positive(message = "The environment ID must be a positive number")
        Long id,

        @NotBlank(message = "The indoor environment name must not be blank")
        String name,

        List<IndoorEnvironmentDTO> childsIndoorEnvironments,

        @NotNull(message = "At least one beacon signal statistic must be provided")
        @Valid
        List<BeaconSignalStatisticsDTO> beaconsSignalStatistics
) {
    public IndoorEnvironmentDTO
     {
        if (childsIndoorEnvironments == null) {
            childsIndoorEnvironments = List.of();
        }
    }

    public IndoorEnvironmentDTO(
            String name,
            List<IndoorEnvironmentDTO> childsIndoorEnvironments,
            List<BeaconSignalStatisticsDTO> beaconSignalStatistics
    ) {
        this(
                null,
                name,
                childsIndoorEnvironments,
                beaconSignalStatistics
        );
    }

    public static IndoorEnvironmentDTO fromDomain(IndoorEnvironmentEntity domain) {

        List<BeaconSignalStatisticsDTO> beaconSignalStatistics =
                domain.getBeaconsSignalStatistics()
                        .stream()
                        .map(BeaconSignalStatisticsDTO::fromDomain)
                        .toList();

        List<IndoorEnvironmentDTO> childsIndoorEnvironments =
                domain.getChildren()
                        .stream()
                        .map(IndoorEnvironmentDTO::fromDomain)
                        .toList();

        return new IndoorEnvironmentDTO(
                domain.getId(),
                domain.getName(),
                childsIndoorEnvironments,
                beaconSignalStatistics
        );
    }
}
