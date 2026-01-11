package lsdi.IndoorBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.IndoorEnvironmentDTO;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class IndoorEnvironment {
    private Long id;
    private String name;
    private List<IndoorEnvironment> childsIndoorEnvironments;
    private List<BeaconSignalStatistics> beaconsSignalStatistics;

    public IndoorEnvironment (
            String name,
            List<IndoorEnvironment> childsIndoorEnvironments,
            List<BeaconSignalStatistics> beaconsSignalStatistics
    ) {
        this.name = name;
        this.beaconsSignalStatistics = beaconsSignalStatistics;
        this.childsIndoorEnvironments = childsIndoorEnvironments;
    }

    public IndoorEnvironment (
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
        this.childsIndoorEnvironments = new ArrayList<>();
    }

    public void addChildIndoorEnvironment(IndoorEnvironment indoorEnvironment) {
        this.childsIndoorEnvironments.add(indoorEnvironment);
    }

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

            List<IndoorEnvironment> childsIndoorEnvironments =
                    dto.childsIndoorEnvironments()
                            .stream()
                            .map(Mapper::fromDTO)
                            .toList();

            return new IndoorEnvironment(
                    dto.name(),
                    childsIndoorEnvironments,
                    beaconSignalStatistics
            );
        }
    }
}
