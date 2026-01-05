package lsdi.IndoorBackend.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lsdi.IndoorBackend.dtos.BeaconFingerprintDTO;

import java.util.Objects;

@Getter
public class BeaconFingerprint {
    private final String macAddress;
    private final float mean;
    private final double std;
    private final long timestampMean;
    private final double variance;
    private final float weight;

    @JsonCreator
    public BeaconFingerprint(
            @JsonProperty("macAddress") String macAddress,
            @JsonProperty("mean") float mean,
            @JsonProperty("std") double std,
            @JsonProperty("timestampMean") long timestampMean,
            @JsonProperty("variance") double variance,
            @JsonProperty("weight") float weight
    ) {
        this.macAddress = Objects.requireNonNull(macAddress);
        this.mean = mean;
        this.std = std;
        this.timestampMean = timestampMean;
        this.variance = variance;
        this.weight = weight;

        isValidMacAddress(macAddress);
    }

    public static void isValidMacAddress(String macAddress) {
        String selectSymbolsMac = "[^0-9A-Fa-f]";
        String normalizedMacAddress = macAddress.replaceAll(selectSymbolsMac, "");

        if (!normalizedMacAddress.matches("[0-9A-Fa-f]{12}")) {
            throw new IllegalArgumentException("Invalid MAC address format");
        }
    }


    public static class Mapper {
        public static BeaconFingerprint fromDTO(BeaconFingerprintDTO dto) {

            return new BeaconFingerprint(
                dto.mac(),
                dto.mean(),
                dto.std(),
                dto.timestampMean(),
                dto.variance(),
                dto.weight()
            );
        }
    }
}
