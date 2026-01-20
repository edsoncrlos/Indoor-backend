package lsdi.IndoorBackend.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.IndoorEnvironmentDTO;
import lsdi.IndoorBackend.repositories.projections.IndoorEnvironmentProjection;

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

        public static IndoorEnvironment fromProjection(
                List<IndoorEnvironmentProjection> projections,
                Long searchId
        ) {
            int length = projections.size();

            // get last
            IndoorEnvironmentProjection last = projections.get(length - 1);

            if (!last.getId().equals(searchId)) {
                for (int i = length - 2; i >= 0; i--) {
                    IndoorEnvironmentProjection p = projections.get(i);
                    if (p.getId().equals(searchId)) {
                        last = p;
                        break;
                    }
                }
            }

            IndoorEnvironment lastIndoor = null;

            // create hierarchy bottom top
            for (int i = length - 1; i >= 0; i--) {

                IndoorEnvironment current = new IndoorEnvironment(last.getId(), last.getName());

                if (lastIndoor != null) {
                    current.addChildIndoorEnvironment(lastIndoor);
                }
                lastIndoor = current;

                // get father
                if (last.getParentId() == null) {
                    break;
                }

                for (int j = length - 1; j >= 0; j--) {
                    IndoorEnvironmentProjection candidate = projections.get(j);
                    if (last.getParentId().equals(candidate.getId())) {
                        last = candidate;
                        break;
                    }
                }
            }

            return lastIndoor;
        }

    }
}
