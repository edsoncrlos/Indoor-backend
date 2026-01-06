package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import lsdi.IndoorBackend.domain.model.BeaconFingerprint;

public record BeaconFingerprintDTO(
        @NotBlank
        String mac,
        float mean,
        double std,
        long timestampMean,
        double variance,
        float weight
) {
    public static BeaconFingerprintDTO fromDomain(BeaconFingerprint domain) {
        return new BeaconFingerprintDTO(
            domain.getMacAddress(),
            domain.getMean(),
            domain.getStd(),
            domain.getTimestampMean(),
            domain.getVariance(),
            domain.getWeight()
        );
    }
}
