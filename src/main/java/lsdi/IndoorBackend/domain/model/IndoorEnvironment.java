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
    private List<IndoorEnvironment> children;
    private List<BeaconSignalStatistics> beaconsSignalStatistics;

    public IndoorEnvironment (
            String name,
            List<IndoorEnvironment> children,
            List<BeaconSignalStatistics> beaconsSignalStatistics
    ) {
        this.name = name;
        this.beaconsSignalStatistics = beaconsSignalStatistics;
        this.children = children;
    }

    public IndoorEnvironment (
            Long id,
            String name
    ) {
        this.id = id;
        this.name = name;
        this.children = new ArrayList<>();
    }

    public void addChildIndoorEnvironment(IndoorEnvironment indoorEnvironment) {
        this.children.add(indoorEnvironment);
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

            List<IndoorEnvironment> children =
                    dto.childsIndoorEnvironments()
                            .stream()
                            .map(Mapper::fromDTO)
                            .toList();

            return new IndoorEnvironment(
                    dto.name(),
                    children,
                    beaconSignalStatistics
            );
        }
    }
}
