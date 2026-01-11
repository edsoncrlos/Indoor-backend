package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;

import java.util.List;

public record IndoorEnvironmentDTO(
        @Positive
        Long id,
        @NotBlank
        String name,
        List<IndoorEnvironmentDTO> childsIndoorEnvironments,
        @NotNull
        List<BeaconSignalStatisticsDTO> beaconsSignalStatistics
) {
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
                domain.getChildIndoorEnvironments()
                        .stream()
                        .map(IndoorEnvironmentDTO::fromDomain)
                        .toList();

        return new IndoorEnvironmentDTO(
                domain.getId(),
                domain.getEnvironmentName(),
                childsIndoorEnvironments,
                beaconSignalStatistics
        );
    }
}
