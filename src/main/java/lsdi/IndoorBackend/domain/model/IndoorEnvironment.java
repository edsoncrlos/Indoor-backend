package lsdi.IndoorBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.IndoorEnvironmentDTO;

import java.util.List;

@AllArgsConstructor
@Getter
public class IndoorEnvironment {
    private String name;
    private List<BeaconFingerprint> beaconFingerprints;

    public int numberBeacons() {
        return beaconFingerprints.size();
    }

    public static class Mapper {
        public static IndoorEnvironment fromDTO(IndoorEnvironmentDTO dto) {
            List<BeaconFingerprint> beaconFingerprints =
                    dto.beaconFingerprints()
                            .stream()
                            .map(BeaconFingerprint.Mapper::fromDTO)
                            .toList();

            return new IndoorEnvironment(
                    dto.name(),
                    beaconFingerprints
            );
        }
    }
}
