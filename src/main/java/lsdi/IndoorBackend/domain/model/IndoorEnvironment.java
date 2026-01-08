package lsdi.IndoorBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.IndoorEnvironmentDTO;

import java.util.List;

@AllArgsConstructor
@Getter
public class IndoorEnvironment {
    private String name;
    private List<BeaconSignalStatistics> beaconsSignalStatistics;

    public int numberBeacons() {
        return beaconsSignalStatistics.size();
    }

    public static class Mapper {
        public static IndoorEnvironment fromDTO(IndoorEnvironmentDTO dto) {
            List<BeaconSignalStatistics> beaconSignalStatistics =
                    dto.beaconsSignalStatistics()
                            .stream()
                            .map(BeaconSignalStatistics.Mapper::fromDTO)
                            .toList();

            return new IndoorEnvironment(
                    dto.name(),
                    beaconSignalStatistics
            );
        }
    }
}
