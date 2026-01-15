package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lsdi.IndoorBackend.domain.model.BeaconSignalStatistics;

public record BeaconSignalStatisticsDTO(
        @NotBlank(message = "The MAC address must not be blank")
        String mac,

        @NotNull(message = "The mean value must be provided")
        Float mean,

        @NotNull(message = "The std must be provided")
        Double std,

        @NotNull(message = "The timestamp mean must be provided")
        Long timestampMean,

        @NotNull(message = "The variance must be provided")
        Double variance,

        @NotNull(message = "The weight must be provided")
        Float weight
) {
    public static BeaconSignalStatisticsDTO fromDomain(BeaconSignalStatistics domain) {
        return new BeaconSignalStatisticsDTO(
                domain.getMacAddress(),
                domain.getMean(),
                domain.getStd(),
                domain.getTimestampMean(),
                domain.getVariance(),
                domain.getWeight()
        );
    }
}