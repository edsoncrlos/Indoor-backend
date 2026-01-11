package lsdi.IndoorBackend.common;

import lsdi.IndoorBackend.dtos.BeaconSignalStatisticsDTO;
import lsdi.IndoorBackend.dtos.IndoorEnvironmentDTO;

import java.util.List;

public class InferenceConstants {

    public static List<IndoorEnvironmentDTO> indoorEnvironmentsDTO = List.of(
            new IndoorEnvironmentDTO(
                    "ets",
                    List.of(),
                    List.of(
                            new BeaconSignalStatisticsDTO(
                                    "AA:BB:CC:DD:EE:01",
                                    -65.3f,
                                    4.2,
                                    1698765432000L,
                                    17.64,
                                    0.8f
                            ),
                            new BeaconSignalStatisticsDTO(
                                    "AA:BB:CC:DD:EE:02",
                                    -67.0f,
                                    3.8,
                                    1698765435000L,
                                    14.44,
                                    0.7f
                            ),
                            new BeaconSignalStatisticsDTO(
                                    "AA:BB:CC:DD:EE:03",
                                    -70.1f,
                                    5.0,
                                    1698765440000L,
                                    25.0,
                                    0.6f
                            )
                    )
            ),
            new IndoorEnvironmentDTO(
                    "meet",
                    List.of(),
                    List.of(
                            new BeaconSignalStatisticsDTO(
                                    "11:22:33:44:55:01",
                                    -60.0f,
                                    3.0,
                                    1698765500000L,
                                    9.0,
                                    1.0f
                            ),
                            new BeaconSignalStatisticsDTO(
                                    "11:22:33:44:55:02",
                                    -62.5f,
                                    3.5,
                                    1698765505000L,
                                    12.25,
                                    0.9f
                            ),
                            new BeaconSignalStatisticsDTO(
                                    "11:22:33:44:55:03",
                                    -64.0f,
                                    4.0,
                                    1698765510000L,
                                    16.0,
                                    0.85f
                            )
                    )
            )
    );
}
