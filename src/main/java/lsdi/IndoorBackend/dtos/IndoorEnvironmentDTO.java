package lsdi.IndoorBackend.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record IndoorEnvironmentDTO(
        @NotBlank
        String name,
        @NotNull
        List<BeaconFingerprintDTO> beaconFingerprints
) {
}
