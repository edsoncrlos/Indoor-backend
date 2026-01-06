package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lsdi.IndoorBackend.entities.IndoorEnvironmentEntity;

import java.util.List;

public record IndoorEnvironmentDTO(
        @NotBlank
        String name,
        @NotNull
        List<BeaconFingerprintDTO> beaconFingerprints
) {
    public static IndoorEnvironmentDTO fromDomain(IndoorEnvironmentEntity domain) {

        List<BeaconFingerprintDTO> fingerprints =
                domain.getBeaconsFingerprints()
                        .stream()
                        .map(BeaconFingerprintDTO::fromDomain)
                        .toList();

        return new IndoorEnvironmentDTO(
                domain.getEnvironmentName(),
                fingerprints
        );
    }
}
