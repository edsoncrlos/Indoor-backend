package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;

import java.util.List;

public record IndoorEnvironmentDTO(
        @NotBlank
        String name,
        @NotNull
        List<BeaconSignalStatisticsDTO> beaconsSignalStatistics
) {
    public static IndoorEnvironmentDTO fromDomain(IndoorEnvironmentEntity domain) {

        List<BeaconSignalStatisticsDTO> beaconSignalStatistics =
                domain.getBeaconsSignalStatistics()
                        .stream()
                        .map(BeaconSignalStatisticsDTO::fromDomain)
                        .toList();

        return new IndoorEnvironmentDTO(
                domain.getEnvironmentName(),
                beaconSignalStatistics
        );
    }
}
