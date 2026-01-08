package lsdi.IndoorBackend.entities.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lsdi.IndoorBackend.domain.model.BeaconSignalStatistics;

import java.util.List;

@Converter
public class BeaconsSignalStatisticsJsonConverter
        implements AttributeConverter<List<BeaconSignalStatistics>, String> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<BeaconSignalStatistics> attribute) {
        try {
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("malformatted json", e);
        }
    }

    @Override
    public List<BeaconSignalStatistics> convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(
                    dbData,
                    new TypeReference<List<BeaconSignalStatistics>>() {}
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deserializing JSON", e);
        }
    }
}
