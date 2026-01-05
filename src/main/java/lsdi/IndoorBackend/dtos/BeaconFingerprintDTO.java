package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;

public record BeaconFingerprintDTO(
        @NotBlank
        String mac,
        float mean,
        double std,
        long timestampMean,
        double variance,
        float weight
) {
}
