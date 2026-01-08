package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import lsdi.IndoorBackend.domain.model.BeaconSignalStatistics;

public record BeaconSignalStatisticsDTO(
        @NotBlank
        String mac,
        float mean,
        double std,
        long timestampMean,
        double variance,
        float weight
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